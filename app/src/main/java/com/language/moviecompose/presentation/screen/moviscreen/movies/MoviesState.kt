package com.language.moviecompose.presentation.screen.moviscreen.movies

import com.language.moviecompose.domain.model.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val search: String = "superman"

)