package com.kobus.pitzer.cinephind.repository.models

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Source") var Source: String,
    @SerializedName("Value") var Value: String
)