package com.kobus.pitzer.cinephind.repository

import androidx.lifecycle.LiveData
import com.kobus.pitzer.cinephind.repository.resource.Resource

interface RandomWordRepository {
    fun getRandomWord(): LiveData<Resource<String>>
}