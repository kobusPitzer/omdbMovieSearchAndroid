package com.kobus.pitzer.cinephind.repository.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kobus.pitzer.cinephind.repository.MovieRepository
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.resource.Resource

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {


    internal var moviesSearchedSource: LiveData<Resource<List<SearchModel?>>>? =
        null
    val moviesSearchedValue: MediatorLiveData<Resource<List<SearchModel?>>> =
        MediatorLiveData()

    internal var movieSearched: LiveData<Resource<MovieDetail>> =
        MediatorLiveData()


    var currentMovieImage: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieName: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieRelease: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieRuntime: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieGenre: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieDirector: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieRating: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieVotes: MediatorLiveData<String> = MediatorLiveData()
    var currentMovieLanguage: MediatorLiveData<String> = MediatorLiveData()
    var currentMoviePlot: MediatorLiveData<String> = MediatorLiveData()
    var currentItemType: MediatorLiveData<String> = MediatorLiveData()
    var isFavourited: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        postInitialValues()
    }

    fun updateFavourite(movie: MovieDetail) {
        movieRepository.toggleFavourite(movie)
        isFavourited.postValue(!isFavourited.value!!)
    }

    fun getMovies() {
        if (moviesSearchedSource != null)
            moviesSearchedValue.removeSource(moviesSearchedSource!!)
        moviesSearchedSource = movieRepository.getMoviesSearch("movie")
        moviesSearchedValue.addSource(moviesSearchedSource!!) {
            moviesSearchedValue.postValue(it)
        }
    }

    fun searchMovies(searchText: String) {
        if (moviesSearchedSource != null)
            moviesSearchedValue.removeSource(moviesSearchedSource!!)
        moviesSearchedSource = movieRepository.getMoviesSearch(searchText)
        moviesSearchedValue.addSource(moviesSearchedSource!!) {
            moviesSearchedValue.postValue(it)
        }
    }

    fun searchMovieByID(id: String) {
        movieSearched = movieRepository.getMovieByID(id)
        addMovieSources()
    }


    private fun postInitialValues() {
        currentMovieImage.postValue("~")
        currentMovieName.postValue("~")
        currentItemType.postValue("movie")
        currentMovieRelease.postValue("~")
        currentMovieRuntime.postValue("~")
        currentMovieGenre.postValue("~")
        currentMovieDirector.postValue("~")
        currentMovieRating.postValue("~")
        currentMovieVotes.postValue("~")
        currentMovieLanguage.postValue("~")
        currentMoviePlot.postValue("~")
        isFavourited.postValue(false)
    }

    private fun addMovieSources() {
        currentMovieImage.addSource(movieSearched) { movieResource ->
            currentMovieImage.postValue(movieResource.data?.Poster.let {
                updateImageSizeIfPossible(it)
            })
        }

        currentMovieName.addSource(movieSearched) { movieResource ->
            currentMovieName.postValue(movieResource.data?.Title.let {
                it
            })
        }

        currentMovieRelease.addSource(movieSearched) { movieResource ->
            currentMovieRelease.postValue(movieResource.data?.Released.let {
                it
            })
        }

        currentMovieRuntime.addSource(movieSearched) { movieResource ->
            currentMovieRuntime.postValue(movieResource.data?.Runtime.let {
                it
            })
        }


        currentMovieGenre.addSource(movieSearched) { movieResource ->
            currentMovieGenre.postValue(movieResource.data?.Genre.let {
                it
            })
        }


        currentMovieDirector.addSource(movieSearched) { movieResource ->
            currentMovieDirector.postValue(movieResource.data?.Director.let {
                it
            })
        }


        currentMovieRating.addSource(movieSearched) { movieResource ->
            currentMovieRating.postValue(movieResource.data?.imdbRating.let {
                it
            })
        }

        currentMovieVotes.addSource(movieSearched) { movieResource ->
            currentMovieVotes.postValue(movieResource.data?.imdbVotes.let {
                it
            })
        }

        currentMovieLanguage.addSource(movieSearched) { movieResource ->
            currentMovieLanguage.postValue(movieResource.data?.Language.let {
                it
            })
        }

        currentMoviePlot.addSource(movieSearched) { movieResource ->
            currentMoviePlot.postValue(movieResource.data?.Plot.let {
                it
            })
        }

        isFavourited.addSource(movieSearched) { movieResource ->
            isFavourited.postValue(movieResource.data?.isFavourited.let {
                it
            })
        }
    }

    fun updateImageSizeIfPossible(movieUrl: String?): String? {
        if (movieUrl?.contains("300.jpg") == true)
            return movieUrl.replace("300.jpg", "900.jpg")
        return movieUrl
    }

}