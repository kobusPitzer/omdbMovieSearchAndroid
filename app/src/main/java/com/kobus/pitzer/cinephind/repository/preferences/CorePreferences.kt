package com.kobus.pitzer.cinephind.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object CorePreferences {
    private const val CORE_SHARED_PREFERENCES = "CORE_SHARED_PREFERENCES"

    private const val KEY_TUT_SCREEN = "KEY_TUT_SCREEN"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(appContext: Context) {
        sharedPreferences =
            appContext.getSharedPreferences(CORE_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getBaseUrl(): String {
        return "http://www.omdbapi.com/"
    }

    fun getWordSearchBaseUrl(): String {
        return "https://random-word-api.herokuapp.com/"
    }

    fun getHasSeenTutorialScreen(): Boolean {
        return sharedPreferences.getBoolean(KEY_TUT_SCREEN, false) ?: false
    }

    fun setHasSeenTutorialScreen(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_TUT_SCREEN, value)
        }
    }

}