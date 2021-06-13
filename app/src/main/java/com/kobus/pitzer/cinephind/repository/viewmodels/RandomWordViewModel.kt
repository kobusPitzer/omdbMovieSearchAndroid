package com.kobus.pitzer.cinephind.repository.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kobus.pitzer.cinephind.repository.RandomWordRepository
import com.kobus.pitzer.cinephind.repository.resource.Resource

class RandomWordViewModel(
    private val randomWordRepository: RandomWordRepository
) : ViewModel() {


    init {
    }

    fun getRandomWord(): LiveData<Resource<String>> {
        return randomWordRepository.getRandomWord()
    }
}