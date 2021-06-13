package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import com.kobus.pitzer.cinephind.repository.network.SeriesService
import com.kobus.pitzer.cinephind.repository.resource.GetSeriesByIDSearchResource
import com.kobus.pitzer.cinephind.repository.resource.GetSeriesSearchResource
import com.kobus.pitzer.cinephind.repository.resource.Resource

class SeriesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
    private val seriesService: SeriesService
) : SeriesRepository {

    override fun getSeriesSearch(searchString: String): LiveData<Resource<List<SearchModel?>>> {
        return GetSeriesSearchResource(
            appExecutors,
            seriesService,
            appDatabase,
            searchString
        ).getResult()
    }

    override fun getSeriesByID(id: String): LiveData<Resource<SeriesDetailModel>> {
        return GetSeriesByIDSearchResource(
            appExecutors,
            seriesService,
            appDatabase,
            id
        ).getResult()
    }


    override fun toggleFavourite(series: SeriesDetailModel) {
        appExecutors.diskIO().execute {
            appDatabase.seriesDao().toggleSeriesFavourite(series)
        }
    }

}