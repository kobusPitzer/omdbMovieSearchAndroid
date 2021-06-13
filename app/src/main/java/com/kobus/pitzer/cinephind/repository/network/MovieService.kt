package com.kobus.pitzer.cinephind.repository.network

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.BuildConfig
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("?type=movie&apikey=" + BuildConfig.OMDb_API_KEY)
    fun getMoviesBySearch(
        @Query("s") search: String
    ): LiveData<ApiResponse<CollectionSearch>>

    @GET("?plot=full&apikey=" + BuildConfig.OMDb_API_KEY)
    fun getMovieByID(
        @Query("i") id: String
    ): LiveData<ApiResponse<MovieDetail>>
}