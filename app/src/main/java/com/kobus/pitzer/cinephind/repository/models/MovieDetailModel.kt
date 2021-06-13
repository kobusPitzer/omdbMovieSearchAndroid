package com.kobus.pitzer.cinephind.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieDetail(
    @SerializedName("Actors") var Actors: String,
    @SerializedName("Awards") var Awards: String,
    @SerializedName("BoxOffice") var BoxOffice: String,
    @SerializedName("Country") var Country: String,
    @SerializedName("DVD") var DVD: String,
    @SerializedName("Director") var Director: String,
    @SerializedName("Genre") var Genre: String,
    @SerializedName("Language") var Language: String,
    @SerializedName("Metascore") var Metascore: String,
    @SerializedName("Plot") var Plot: String,
    @SerializedName("Poster") var Poster: String,
    @SerializedName("Production") var Production: String,
    @SerializedName("Rated") var Rated: String,
    @SerializedName("Ratings") var Ratings: List<Rating>,
    @SerializedName("Released") var Released: String,
    @SerializedName("Response") var Response: String,
    @SerializedName("Runtime") var Runtime: String,
    @SerializedName("Title") var Title: String,
    @SerializedName("Type") var Type: String,
    @SerializedName("Website") var Website: String,
    @SerializedName("Writer") var Writer: String,
    @SerializedName("Year") var Year: String,
    @PrimaryKey
    @SerializedName("imdbID") var imdbID: String,
    @SerializedName("imdbRating") var imdbRating: String,
    @SerializedName("imdbVotes") var imdbVotes: String,
    @SerializedName("isfavourited") var isFavourited: Boolean = false
)