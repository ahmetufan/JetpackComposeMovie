package com.language.moviecompose.presentation.screen.moviscreen.movie_detail

import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesEvent

sealed class MoviesDetailEvent {

    class SnackBarError(val message: String?) : MoviesDetailEvent()

}
