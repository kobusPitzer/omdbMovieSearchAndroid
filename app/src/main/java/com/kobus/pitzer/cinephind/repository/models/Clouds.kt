package com.kobus.pitzer.cinephind.repository.models

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all") var all: Int? = null
)