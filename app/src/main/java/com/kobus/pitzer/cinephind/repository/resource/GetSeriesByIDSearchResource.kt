package com.kobus.pitzer.cinephind.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import com.kobus.pitzer.cinephind.repository.network.ApiResponse
import com.kobus.pitzer.cinephind.repository.network.SeriesService

class GetSeriesByIDSearchResource(
    appExecutors: AppExecutors,
    seriesService: SeriesService,
    db: AppDatabase,
    id: String
) {

    private var networkBoundResource =
        object : NetworkBoundResource<SeriesDetailModel, SeriesDetailModel>(appExecutors) {
            override fun saveCallResult(item: SeriesDetailModel?) {
                item?.let { db.seriesDao().addSeriesSearchedByIDData(it) }
            }

            override fun loadFromDb(): LiveData<SeriesDetailModel> {
                return db.seriesDao().getSeriesByID(id)
            }

            override fun createCall(): LiveData<ApiResponse<SeriesDetailModel>> {
                return seriesService.getSeriesByID(id)
            }

            override fun shouldFetch(data: SeriesDetailModel?): Boolean {
                return true
            }

        }

    fun getResult(): LiveData<Resource<SeriesDetailModel>> {
        return networkBoundResource.asLiveData()
    }
}