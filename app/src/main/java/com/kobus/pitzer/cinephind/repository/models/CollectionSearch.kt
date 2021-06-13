package com.kobus.pitzer.cinephind.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CollectionSearch(
    @PrimaryKey
    var id: Long = 0,
    @SerializedName("Response") var response: Boolean,
    @SerializedName("Search") var searches: List<SearchModel>,
    @SerializedName("totalResults") var totalResults: Int
)