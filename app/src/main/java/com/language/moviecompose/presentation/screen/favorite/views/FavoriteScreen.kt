package com.language.moviecompose.presentation.screen.favorite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.language.moviecompose.presentation.navigation.BottomNavigationBar
import com.language.moviecompose.presentation.navigation.Destination
import com.language.moviecompose.presentation.screen.favorite.FavoriteViewModel
import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesEvent

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()) {

    // Composable yüklendiğinde filmleri çekme işlemi LaunchedEffect kullanarak yapıyoruz. LaunchedEffect, coroutine scope sağlar ve composable'in lifecycle'ına bağlıdır
    LaunchedEffect(key1 = true) {
        viewModel.getAllFavoriteMovie()
    }

    // Burada favoriMovies LiveData'nın son değerini observe ediyoruz ve her değişiklikte UI'ı güncelliyoruz.
    val favoriteMovies by viewModel.favoriteMovies.observeAsState(initial = listOf())

    val isRefreshing by viewModel.isRefreshing.observeAsState(initial = false)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {

            Column() {

                Text(
                    text = "Favorite Movie",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(14.dp),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = viewModel::getAllFavoriteMovie
                ) {

                    LazyColumn {
                        items(favoriteMovies) { movie ->

                            FavoriteListRow(movie = movie, onItemClick = {
                                //Detail ekranına imdbId yollama
                                navController.navigate(Destination.MovieDetailScreen.route + "/${movie.imdbID}")
                            })

                        }
                    }
                }


            }
        }
    }
}