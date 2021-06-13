package com.kobus.pitzer.cinephind.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SearchModel(
    @SerializedName("Poster") var posterURL: String,
    @SerializedName("Title") var title: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Year") var year: String,

    @PrimaryKey
    @SerializedName("imdbID") var imdbID: String
)