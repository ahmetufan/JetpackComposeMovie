package com.language.moviecompose.presentation.screen.moviscreen.movie_detail

import com.language.moviecompose.domain.model.MovieDetail

data class MovieDetailState (
    val isLoading : Boolean = false,
    val movie : MovieDetail? = null,
    val checkExistMovie : Int? = 0,
    val error : String = ""
)