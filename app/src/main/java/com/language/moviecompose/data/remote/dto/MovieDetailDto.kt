package com.language.moviecompose.data.remote.dto

import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.domain.model.MovieDetail

data class MovieDetailDto(
    val Actors: String,
    val Awards: String,
    val BoxOffice: String,
    val Country: String,
    val DVD: String,
    val Director: String,
    val Genre: String,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Production: String,
    val Rated: String,
    val Ratings: List<Rating>,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Website: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String
)

//MoviesDetailDto sınıfına extension ekleyerek film listesi detayları elde ediyoruz
fun MovieDetailDto.toMovieDetail() : MovieDetail {
    return MovieDetail(Actors, Awards, Country, Director, Genre, Language, Poster, Rated, Released, Title, Type, Year, imdbRating,imdbID,Plot)
}

fun MovieDetail.toMovieLocal() : MovieLocalModel {
    return MovieLocalModel(Poster, Title, Year, Type, imdbID)
}







