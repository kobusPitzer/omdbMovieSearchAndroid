package com.kobus.pitzer.cinephind.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel

@Dao
interface SeriesDao {

    @Query("select * from SearchModel where type = 'series' and title like '%' || :search || '%' order by year collate NOCASE DESC")
    fun getSeriesSearched(search: String): LiveData<List<SearchModel?>>

    @Query("select * from SeriesDetailModel where imdbID = :id")
    fun getSeriesByID(id: String): LiveData<SeriesDetailModel>

    @Query("select * from SeriesDetailModel where imdbID = :id")
    fun getSeriesByIDVal(id: String): SeriesDetailModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSeriesSearchedData(data: SearchModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSeriesSearchedByIDDataToDB(data: SeriesDetailModel)

    @Transaction
    fun addSeriesSearchData(data: CollectionSearch) {
        data?.searches?.forEach {
            if (it != null)
                addSeriesSearchedData(it)
        }
    }

    @Transaction
    fun addSeriesSearchedByIDData(data: SeriesDetailModel) {
        val existingMovie = getSeriesByIDVal(data.imdbID)
        if (existingMovie?.isFavourited == true)
            data.isFavourited = true
        addSeriesSearchedByIDDataToDB(data)
    }


    @Transaction
    fun toggleSeriesFavourite(data: SeriesDetailModel) {
        data.isFavourited = !data.isFavourited
        addSeriesSearchedByIDDataToDB(data)
    }
}