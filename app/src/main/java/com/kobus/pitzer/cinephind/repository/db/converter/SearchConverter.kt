package com.kobus.pitzer.cinephind.repository.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kobus.pitzer.cinephind.repository.models.SearchModel

class SearchConverter {
    @TypeConverter
    fun stringToList(json: String?): List<SearchModel>? {
        return if (json == null) {
            null
        } else {
            val type = object : TypeToken<List<SearchModel>>() {

            }.type
            Gson().fromJson<List<SearchModel>>(json, type)
        }
    }

    @TypeConverter
    fun listToString(list: List<SearchModel>?): String? {
        return if (list == null) {
            null
        } else {
            val type = object : TypeToken<List<SearchModel>>() {

            }.type
            Gson().toJson(list, type)
        }
    }
}