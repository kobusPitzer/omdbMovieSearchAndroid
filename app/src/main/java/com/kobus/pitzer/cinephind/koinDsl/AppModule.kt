package com.kobus.pitzer.cinephind.koinDsl

import androidx.room.Room
import com.kobus.pitzer.cinephind.repository.*
import com.kobus.pitzer.cinephind.repository.constant.DbFile
import com.kobus.pitzer.cinephind.repository.core.AppExecutors
import com.kobus.pitzer.cinephind.repository.db.AppDatabase
import com.kobus.pitzer.cinephind.repository.db.AppDatabaseCallback
import com.kobus.pitzer.cinephind.repository.db.DBHelper
import com.kobus.pitzer.cinephind.repository.network.*
import com.kobus.pitzer.cinephind.repository.preferences.CorePreferences
import com.kobus.pitzer.cinephind.repository.viewmodels.MovieViewModel
import com.kobus.pitzer.cinephind.repository.viewmodels.RandomWordViewModel
import com.kobus.pitzer.cinephind.repository.viewmodels.SeriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {
    single { AppExecutors.createInstance() }

    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get(), get()) }
    single<RandomWordRepository> { RandomWordRepositoryImpl(get(), get(), get()) }

    // Network Services  - Auth required
    single {
        ServiceGenerator(
            CorePreferences.getBaseUrl()
        )
    }

    single {
        WordServiceGenerator(
            CorePreferences.getWordSearchBaseUrl()
        )
    }

    single { (get() as ServiceGenerator).createService(MovieService::class.java) }
    single { (get() as ServiceGenerator).createService(SeriesService::class.java) }
    single { (get() as WordServiceGenerator).createService(WordService::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DbFile.APP_DB_FILE.fileName
        )
            .openHelperFactory(DBHelper.getPassPhrase())
            .fallbackToDestructiveMigration()
            .addCallback(AppDatabaseCallback(get()))
            .build()
    }

    viewModel { MovieViewModel(get()) }
    viewModel { SeriesViewModel(get()) }
    viewModel { RandomWordViewModel(get()) }
}