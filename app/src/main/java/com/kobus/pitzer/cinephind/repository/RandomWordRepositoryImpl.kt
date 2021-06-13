package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.network.WordService
import com.kobus.pitzer.cinephind.repository.resource.GetRandomWordResource
import com.kobus.pitzer.cinephind.repository.resource.Resource

class RandomWordRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val appExecutors: AppExecutors,
    private val wordService: WordService
) : RandomWordRepository {


    override fun getRandomWord(): LiveData<Resource<String>> {
        return GetRandomWordResource(
            appExecutors,
            wordService

        ).getResult()
    }
}