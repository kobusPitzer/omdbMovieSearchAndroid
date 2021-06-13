package com.kobus.pitzer.cinephind.repository.network

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.BuildConfig
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesService {
    @GET("?type=series&apikey=" + BuildConfig.OMDb_API_KEY)
    fun getSeriesBySearch(
        @Query("s") search: String
    ): LiveData<ApiResponse<CollectionSearch>>

    @GET("?type=episode&apikey=" + BuildConfig.OMDb_API_KEY)
    fun getEpisodesBySearch(
        @Query("s") search: String
    ): LiveData<ApiResponse<CollectionSearch>>

    @GET("?plot=full&apikey=" + BuildConfig.OMDb_API_KEY)
    fun getSeriesByID(
        @Query("i") id: String
    ): LiveData<ApiResponse<SeriesDetailModel>>


}