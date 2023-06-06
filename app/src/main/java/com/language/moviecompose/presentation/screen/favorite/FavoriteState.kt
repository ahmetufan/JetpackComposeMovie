package com.language.moviecompose.presentation.screen.favorite

import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.domain.model.Movie

class FavoriteState {
    val isLoading: Boolean = false
    val movies: List<MovieLocalModel> = emptyList()
    val error: String = ""
}