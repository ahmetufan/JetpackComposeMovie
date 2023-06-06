package com.language.moviecompose.presentation.screen.moviscreen.movies.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
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
import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesEvent
import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesViewModel
import kotlinx.coroutines.launch
@Composable
fun MovieScreen(navController: NavController, viewModel: MoviesViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    // Search text'ini hatırla
    var searchText by remember { mutableStateOf("Superman") }

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect {
                when (it) {
                    is MoviesEvent.SnackBarError -> {
                        snackbarHostState.showSnackbar(message = it.message ?: "",)
                    }
                    else -> {
                        Log.d("uiEvent :", "uiEvent null")
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
        ) {

            Column() {
                MovieSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    hint = viewModel.searchHint,
                    onSearch = { newSearchText ->
                        searchText = newSearchText
                        viewModel.onEvent(MoviesEvent.Search(newSearchText))
                    }
                )

                Text(
                    text = "OMDB Movie",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(14.dp),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
                    onRefresh = { viewModel.onEvent(MoviesEvent.Search(searchText)) }
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.movies) { movie ->
                            MovieListRow(movie = movie, onItemClick = {
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





@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovieSearchBar(modifier: Modifier, hint: String = "", onSearch: (String) -> Unit = {}) {

    var text by remember {
        mutableStateOf("")
    }

    // Tıklanıldığında hint yazısının gözükmemesi için
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {

        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearch(text)
                    keyboardController?.hide()
                }
            ),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )


        if (isHintDisplayed) {
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }


}

