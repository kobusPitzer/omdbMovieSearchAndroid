package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.network.MovieService
import com.kobus.pitzer.cinephind.repository.resource.GetMovieByIDSearchResource
import com.kobus.pitzer.cinephind.repository.resource.GetMoviesSearchResource
import com.kobus.pitzer.cinephind.repository.resource.Resource

class MovieRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
    private val movieService: MovieService
) : MovieRepository {

    override fun getMoviesSearch(searchString: String): LiveData<Resource<List<SearchModel?>>> {
        return GetMoviesSearchResource(
            appExecutors,
            movieService,
            appDatabase,
            searchString
        ).getResult()
    }

    override fun getMovieByID(
        id: String
    ): LiveData<Resource<MovieDetail>> {
        return GetMovieByIDSearchResource(
            appExecutors,
            movieService,
            appDatabase,
            id
        ).getResult()
    }

    override fun toggleFavourite(movie: MovieDetail) {
        appExecutors.diskIO().execute {
            appDatabase.moviesDao().toggleMovieFavourite(movie)
        }
    }

}