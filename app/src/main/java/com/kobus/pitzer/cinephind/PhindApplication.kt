package com.kobus.pitzer.cinephind


import android.app.Application
import android.os.Build
import android.util.Log
import com.kobus.pitzer.cinephind.koinDsl.appModule
import com.kobus.pitzer.cinephind.logs.FileLoggingTree
import com.kobus.pitzer.cinephind.media.MediaFolders
import com.kobus.pitzer.cinephind.repository.preferences.CorePreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class PhindApplication : Application() {

    private val TAG: String = "PhindApplication"

    override fun onCreate() {
        super.onCreate()

        CorePreferences.initialize(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())


            startKoin {
                object : Logger() {
                    override fun log(level: Level, msg: MESSAGE) {
                        when (level) {
                            Level.DEBUG -> Timber.d(msg)
                            Level.INFO -> Timber.i(msg)
                            Level.ERROR -> Timber.e(msg)
                        }
                    }
                }
                androidContext(this@PhindApplication)
                modules(appModule)
            }

        } else {
            startKoin {
                androidContext(this@PhindApplication)
                modules(appModule)
            }
        }

        try {
            Timber.plant(FileLoggingTree(MediaFolders.getLogsDirectory(this), getLogHeader()))
        } catch (e: Exception) {
            Log.e(TAG, "PhindApplication FileLoggingTree plant failed with exception $e")
        }


    }

    private fun getLogHeader(): String {
        val result = StringBuilder()
        result.append("\n")
            .append("Android Version : " + "\t")
            .append(Build.VERSION.SDK_INT.toString())
            .append("\n").append("Manufacturer : " + "\t").append(Build.MANUFACTURER).append("\n")
            .append("Model : " + "\t").append(
                Build.MODEL
            ).append("\n").append("Brand : " + "\t").append(Build.BRAND).append("\n")

        return result.toString()
    }

}
