package com.language.moviecompose.data.remote.dto

import com.language.moviecompose.domain.model.Movie

data class MoviesDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)

//MoviesDto sınıfına extension ekleyerek film listesi elde ediyoruz
fun MoviesDto.toMovieList(): List<Movie> {
    return Search.map {
        search -> Movie(search.Poster, search.Title, search.Year, search.imdbID,search.Type)
    }
}