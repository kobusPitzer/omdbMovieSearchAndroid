package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.resource.Resource

interface MovieRepository {
    fun getMoviesSearch(searchString: String): LiveData<Resource<List<SearchModel?>>>
    fun getMovieByID(id: String): LiveData<Resource<MovieDetail>>
    fun toggleFavourite(movie: MovieDetail)
}