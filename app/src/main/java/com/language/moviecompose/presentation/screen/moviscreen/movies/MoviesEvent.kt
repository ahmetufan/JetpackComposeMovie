package com.language.moviecompose.presentation.screen.moviscreen.movies

sealed class MoviesEvent {
    class SnackBarError(val message: String?) : MoviesEvent()
    data class Search(val searchString: String) : MoviesEvent()
}