package com.kobus.pitzer.cinephind.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.network.ApiResponse
import com.kobus.pitzer.cinephind.repository.network.MovieService

class GetMoviesSearchResource(
    appExecutors: AppExecutors,
    movieService: MovieService,
    db: AppDatabase,
    search: String
) {

    private var networkBoundResource =
        object : NetworkBoundResource<List<SearchModel?>, CollectionSearch>(appExecutors) {
            override fun saveCallResult(item: CollectionSearch?) {
                if (item?.response != false)
                    item?.let {
                        db.moviesDao().addMoviesSearchData(it)
                    }
            }

            override fun loadFromDb(): LiveData<List<SearchModel?>> {
                return db.moviesDao().getMoviesSearched(search)
            }

            override fun createCall(): LiveData<ApiResponse<CollectionSearch>> {
                return movieService.getMoviesBySearch(search)
            }

            override fun shouldFetch(data: List<SearchModel?>?): Boolean {
                return true
            }

        }

    fun getResult(): LiveData<Resource<List<SearchModel?>>> {
        return networkBoundResource.asLiveData()
    }
}