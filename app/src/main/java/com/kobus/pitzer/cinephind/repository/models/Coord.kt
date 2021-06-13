package com.kobus.pitzer.cinephind.repository.models

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null
)