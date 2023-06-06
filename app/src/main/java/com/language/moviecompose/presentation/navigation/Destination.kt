package com.language.moviecompose.presentation.navigation

sealed class Destination(val route: String) {

    object MovieScreen : Destination("movie_screen")
    object MovieDetailScreen : Destination("movie_detail_screen")
    object FavoriteScreen : Destination("favorite_screen")

}