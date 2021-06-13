package com.kobus.pitzer.cinephind.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.network.ApiResponse
import com.kobus.pitzer.cinephind.repository.network.MovieService

class GetMovieByIDSearchResource(
    appExecutors: AppExecutors,
    movieService: MovieService,
    db: AppDatabase,
    id: String
) {
    private var networkBoundResource =
        object : NetworkBoundResource<MovieDetail, MovieDetail>(appExecutors) {
            override fun saveCallResult(item: MovieDetail?) {
                item?.let { db.moviesDao().addMovieSearchedByIDData(it) }
            }

            override fun loadFromDb(): LiveData<MovieDetail> {
                return db.moviesDao().getMovieByID(id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetail>> {
                return movieService.getMovieByID(id)
            }

            override fun shouldFetch(data: MovieDetail?): Boolean {
                return true
            }

        }

    fun getResult(): LiveData<Resource<MovieDetail>> {
        return networkBoundResource.asLiveData()
    }
}