package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import com.kobus.pitzer.cinephind.repository.resource.Resource

interface SeriesRepository {
    fun getSeriesSearch(searchString: String): LiveData<Resource<List<SearchModel?>>>
    fun getSeriesByID(id: String): LiveData<Resource<SeriesDetailModel>>
    fun toggleFavourite(series: SeriesDetailModel)
}