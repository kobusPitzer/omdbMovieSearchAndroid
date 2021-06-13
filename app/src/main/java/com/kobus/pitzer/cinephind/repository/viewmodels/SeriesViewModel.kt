package com.kobus.pitzer.cinephind.repository.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kobus.pitzer.cinephind.repository.SeriesRepository
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.resource.Resource

class SeriesViewModel(
    private val seriesRepository: SeriesRepository
) : ViewModel() {


    internal var seriesSearchedSource: LiveData<Resource<List<SearchModel?>>>? =
        null
    val seriesSearchedValue: MediatorLiveData<Resource<List<SearchModel?>>> =
        MediatorLiveData()

    internal var seriesSearched: LiveData<Resource<SeriesDetailModel>> =
        MediatorLiveData()


    var currentSeriesImage: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesName: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesRelease: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesRuntime: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesGenre: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesDirector: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesRating: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesVotes: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesLanguage: MediatorLiveData<String> = MediatorLiveData()
    var currentSeriesPlot: MediatorLiveData<String> = MediatorLiveData()
    var currentItemType: MediatorLiveData<String> = MediatorLiveData()
    var isFavourited: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        postInitialValues()

        getSeries()
    }

    fun updateFavourite(series: SeriesDetailModel) {
        seriesRepository.toggleFavourite(series)
        isFavourited.postValue(!isFavourited.value!!)
    }

    private fun getSeries() {
        if (seriesSearchedSource != null)
            seriesSearchedValue.removeSource(seriesSearchedSource!!)
        seriesSearchedSource = seriesRepository.getSeriesSearch("series")
        seriesSearchedValue.addSource(seriesSearchedSource!!) {
            seriesSearchedValue.postValue(it)
        }
    }

    fun searchSeries(searchText: String) {
        if (seriesSearchedSource != null)
            seriesSearchedValue.removeSource(seriesSearchedSource!!)
        seriesSearchedSource = seriesRepository.getSeriesSearch(searchText)
        seriesSearchedValue.addSource(seriesSearchedSource!!) {
            seriesSearchedValue.postValue(it)
        }
    }

    fun searchSeriesByID(id: String) {
        seriesSearched = seriesRepository.getSeriesByID(id)
        addSeriesSources()
    }


    private fun postInitialValues() {
        currentSeriesImage.postValue("~")
        currentSeriesName.postValue("~")
        currentItemType.postValue("series")
        currentSeriesRelease.postValue("~")
        currentSeriesRuntime.postValue("~")
        currentSeriesGenre.postValue("~")
        currentSeriesDirector.postValue("~")
        currentSeriesRating.postValue("~")
        currentSeriesVotes.postValue("~")
        currentSeriesLanguage.postValue("~")
        currentSeriesPlot.postValue("~")
        isFavourited.postValue(false)
    }

    private fun addSeriesSources() {
        currentSeriesImage.addSource(seriesSearched) { seriesResource ->
            currentSeriesImage.postValue(seriesResource.data?.Poster.let {
                updateImageSizeIfPossible(it)
            })
        }

        currentSeriesName.addSource(seriesSearched) { seriesResource ->
            currentSeriesName.postValue(seriesResource.data?.Title.let {
                it
            })
        }

        currentSeriesRelease.addSource(seriesSearched) { seriesResource ->
            currentSeriesRelease.postValue(seriesResource.data?.Released.let {
                it
            })
        }

        currentSeriesRuntime.addSource(seriesSearched) { seriesResource ->
            currentSeriesRuntime.postValue(seriesResource.data?.Runtime.let {
                it
            })
        }


        currentSeriesGenre.addSource(seriesSearched) { seriesResource ->
            currentSeriesGenre.postValue(seriesResource.data?.Genre.let {
                it
            })
        }


        currentSeriesDirector.addSource(seriesSearched) { seriesResource ->
            currentSeriesDirector.postValue(seriesResource.data?.Director.let {
                it
            })
        }


        currentSeriesRating.addSource(seriesSearched) { seriesResource ->
            currentSeriesRating.postValue(seriesResource.data?.imdbRating.let {
                it
            })
        }

        currentSeriesVotes.addSource(seriesSearched) { seriesResource ->
            currentSeriesVotes.postValue(seriesResource.data?.imdbVotes.let {
                it
            })
        }

        currentSeriesLanguage.addSource(seriesSearched) { seriesResource ->
            currentSeriesLanguage.postValue(seriesResource.data?.Language.let {
                it
            })
        }

        currentSeriesPlot.addSource(seriesSearched) { seriesResource ->
            currentSeriesPlot.postValue(seriesResource.data?.Plot.let {
                it
            })
        }

        isFavourited.addSource(seriesSearched) { seriesResource ->
            isFavourited.postValue(seriesResource.data?.isFavourited.let {
                it
            })
        }
    }

    fun updateImageSizeIfPossible(seriesUrl: String?): String? {
        if (seriesUrl?.contains("300.jpg") == true)
            return seriesUrl.replace("300.jpg", "900.jpg")
        return seriesUrl
    }

}