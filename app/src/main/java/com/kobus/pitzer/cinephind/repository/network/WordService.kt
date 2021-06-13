package com.kobus.pitzer.cinephind.repository.network

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface WordService {
    @GET("word?swear=0")
    fun getRandomWord(
        @Query("number") search: String
    ): LiveData<ApiResponse<String>>
}