package com.kobus.pitzer.cinephind.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.cinephind.repository.models.Rating

class RatingsConverter {
    @TypeConverter
    fun stringToList(json: String?): List<Rating>? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<List<Rating>>() {

            }.type
            Gson().fromJson<List<Rating>>(json, type)
        }
    }

    @TypeConverter
    fun listToString(list: List<Rating>?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<List<Rating>>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}