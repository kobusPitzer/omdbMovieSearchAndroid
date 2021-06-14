package com.kobus.pitzer.cinephind.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.network.LiveDataCallAdapterFactory
import com.kobus.pitzer.cinephind.repository.network.MovieService
import com.kobus.pitzer.cinephind.utils.LiveDataTestUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MoviesServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(MovieService::class.java)
    }


    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMovies() {
        val searchString = "movies"
        enqueueResponse("movies.json")
        val movieData =
            LiveDataTestUtil.getValue(
                service.getMoviesBySearch(
                    searchString
                )
            ).body

        val request = mockWebServer.takeRequest()

        assertThat(
            request.path,
            `is`("/?type=movie&apikey=pewpewpew&s=movies")
        )

        assertThat<CollectionSearch>(movieData, notNullValue())
        assertThat(movieData?.searches?.size, `is`(10))
        assertThat(movieData?.searches?.get(0)?.imdbID, `is`("tt1490017"))
        assertThat(movieData?.searches?.get(0)?.type, `is`("movie"))
        assertThat(movieData?.searches?.get(0)?.year, `is`("2014"))
        assertThat(movieData?.searches?.get(2)?.title, `is`("Scary Movie"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName")
        if (inputStream == null)
            assert(true)
        inputStream?.source()?.buffer()
        val source = inputStream?.source()?.buffer()
        if (source == null)
            assert(true)
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source!!.readString(Charsets.UTF_8))
        )
    }

}