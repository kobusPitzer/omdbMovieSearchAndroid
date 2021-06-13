package com.kobus.pitzer.cinephind.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.cinephind.repository.models.Wind

class WindConverter {
    @TypeConverter
    fun stringToItem(json: String?): Wind? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<Wind>() {

            }.type
            Gson().fromJson<Wind>(json, type)
        }
    }

    @TypeConverter
    fun itemToString(list: Wind?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<Wind>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}