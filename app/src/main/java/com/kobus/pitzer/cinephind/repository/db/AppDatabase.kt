package com.kobus.pitzer.cinephind.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kobus.pitzer.cinephind.repository.dao.MoviesDao
import com.kobus.pitzer.cinephind.repository.dao.SeriesDao
import com.kobus.pitzer.cinephind.repository.db.converter.*
import com.kobus.pitzer.cinephind.repository.models.CollectionSearch
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel

@Database(
    entities = [
        CollectionSearch::class,
        SearchModel::class,
        MovieDetail::class,
        SeriesDetailModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    JsonArrayConverter::class,
    DateTypeConverter::class,
    CloudsConverter::class,
    CoordConverter::class,
    CurrentConverter::class,
    DailyConverter::class,
    MainConverter::class,
    SysConverter::class,
    SearchConverter::class,
    RatingsConverter::class,
    WeatherXConverter::class,
    WindConverter::class

)

abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun seriesDao(): SeriesDao
}