package com.kobus.pitzer.cinephind.repository.resource

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.network.ApiResponse
import com.kobus.pitzer.cinephind.repository.network.WordService
import timber.log.Timber

class GetRandomWordResource(
    appExecutors: AppExecutors,
    wordService: WordService
) {
    private var networkResource: NetworkResource<String> =
        object : NetworkResource<String>(appExecutors) {

            override fun processCallResult(item: String?) {
                Timber.i("Successfully got random word")
            }

            override fun createCall(): LiveData<ApiResponse<String>> {
                return wordService.getRandomWord("1")
            }
        }

    fun getResult(): LiveData<Resource<String>> {
        return networkResource.asLiveData()
    }
}