package com.kobus.pitzer.cinephind.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.models.SearchModel

@Dao
interface MoviesDao {

    @Query("select * from CollectionSearch")
    fun getLastMovieCall(): LiveData<CollectionSearch?>

    @Query("select * from SearchModel where type = 'movie' and title like '%' || :search || '%' order by year collate NOCASE DESC")
    fun getMoviesSearched(search: String): LiveData<List<SearchModel?>>

    @Query("select * from MovieDetail where imdbID = :id")
    fun getMovieByID(id: String): LiveData<MovieDetail>

    @Query("select * from MovieDetail where imdbID = :id")
    fun getMovieByIDVal(id: String): MovieDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviesSearchedData(data: CollectionSearch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieSearchedData(data: SearchModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieSearchedByIDDataToDB(data: MovieDetail)

    @Transaction
    fun addMoviesSearchData(data: CollectionSearch) {
        data?.searches?.forEach {
            if (it != null)
                addMovieSearchedData(it)
        }
    }

    @Transaction
    fun addMovieSearchedByIDData(data: MovieDetail) {
        val existingMovie = getMovieByIDVal(data.imdbID)
        if (existingMovie?.isFavourited == true)
            data.isFavourited = true
        addMovieSearchedByIDDataToDB(data)
    }

    @Transaction
    fun toggleMovieFavourite(data: MovieDetail) {
        data.isFavourited = !data.isFavourited
        addMovieSearchedByIDDataToDB(data)
    }

}