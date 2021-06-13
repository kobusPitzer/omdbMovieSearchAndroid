package com.kobus.pitzer.cinephind.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kobus.pitzer.cinephind.repository.MovieRepository
import com.kobus.pitzer.cinephind.repository.viewmodels.MovieViewModel
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MovieViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repo = Mockito.mock(MovieRepository::class.java)

    private var movieViewModel: MovieViewModel

    init {
        movieViewModel = MovieViewModel(repo)
    }

    @Test
    fun testPosterUpgrade() {
        var type =
            movieViewModel.updateImageSizeIfPossible("https://m.media-amazon.com/images/M/MV5BMjM0NjMxNjU3MF5BMl5BanBnXkFtZTcwNDYyMjA1OQ@@._V1_SX300.jpg")
        assertThat(type?.contains("SX900.jpg"), CoreMatchers.`is`(true))
        type =
            movieViewModel.updateImageSizeIfPossible("https://m.media-amazon.com/images/M/MV5BMjM0NjMxNjU3MF5BMl5BanBnXkFtZTcwNDYyMjA1OQ@@._V1_SX200.jpg")
        assertThat(type?.contains("SX200.jpg"), CoreMatchers.`is`(true))
    }

}