package com.language.moviecompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieLocalModel(
    val Poster: String,
    val Title: String,
    val Year: String,
    val Type: String,
    @PrimaryKey
    val imdbID: String

)