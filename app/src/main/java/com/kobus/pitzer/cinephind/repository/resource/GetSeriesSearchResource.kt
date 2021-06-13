package com.kobus.pitzer.cinephind.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.network.ApiResponse
import com.kobus.pitzer.cinephind.repository.network.SeriesService

class GetSeriesSearchResource(
    appExecutors: AppExecutors,
    seriesService: SeriesService,
    db: AppDatabase,
    search: String
) {

    private var networkBoundResource =
        object : NetworkBoundResource<List<SearchModel?>, CollectionSearch>(appExecutors) {
            override fun saveCallResult(item: CollectionSearch?) {
                item?.let { db.seriesDao().addSeriesSearchData(it) }
            }

            override fun loadFromDb(): LiveData<List<SearchModel?>> {
                return db.seriesDao().getSeriesSearched(search)
            }

            override fun createCall(): LiveData<ApiResponse<CollectionSearch>> {
                return seriesService.getSeriesBySearch(search)
            }

            override fun shouldFetch(data: List<SearchModel?>?): Boolean {
                return true
            }

        }

    fun getResult(): LiveData<Resource<List<SearchModel?>>> {
        return networkBoundResource.asLiveData()
    }
}