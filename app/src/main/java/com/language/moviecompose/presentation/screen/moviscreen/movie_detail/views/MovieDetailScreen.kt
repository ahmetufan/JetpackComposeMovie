package com.language.moviecompose.presentation.screen.moviscreen.movie_detail.views

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.presentation.screen.moviscreen.movie_detail.MovieDetailViewModel
import com.language.moviecompose.presentation.screen.moviscreen.movie_detail.MoviesDetailEvent
import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesEvent
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect {
                when (it) {
                    is MoviesDetailEvent.SnackBarError -> {
                        snackbarHostState.showSnackbar(message = it.message ?: "",)
                    }
                    else -> {
                        Log.d("uiEvent :", "uiEvent null")
                    }
                }
            }
        }
    }


    Scaffold() { paddingValue ->

        Box(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            state.movie?.let {

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Image(
                        painter = rememberAsyncImagePainter(model = it.Poster),
                        contentDescription = it.Title,
                        modifier = Modifier
                            .padding(top= 16.dp, bottom = 16.dp)
                            .fillMaxWidth()
                            .height(350.dp),
                        contentScale = ContentScale.Fit
                    )


                    Text(
                        text = it.Title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            text = it.imdbRating,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(
                            text = it.Year,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp),
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = { /**/ },
                        modifier = Modifier
                            .border(2.dp, Color.Yellow, RoundedCornerShape(5.dp))
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    ) {
                        Text(
                            text = it.Genre,
                            color = Color.Yellow
                        )
                    }


                    Text(
                        text = "Summary",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 14.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(
                        text = it.Plot,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 7.dp, bottom = 14.dp),
                        color = Color.White
                    )


                    Text(
                        text = "Actors",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 14.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(
                        text = it.Actors,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(7.dp),
                        color = Color.White
                    )

                    Text(
                        text = "Country",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 14.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(
                        text = it.Country,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(7.dp),
                        color = Color.White
                    )


                    Text(
                        text = "Director",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 14.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(
                        text = it.Director,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 7.dp, bottom = 14.dp),
                        color = Color.White
                    )




                    // Local'e kaydetme
                    if (state.checkExistMovie == 0) {
                        Button(
                            onClick = { viewModel.insertMovie(it) },
                            modifier = Modifier
                                .padding(2.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                        ) {
                            Text("KAYDET", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }



                    // Local'den silme
                    if (state.checkExistMovie == 1) {
                        Button(onClick = {viewModel.deleteMovie(movieLocalModel = MovieLocalModel(it.Poster,it.Title,it.Year,it.Type,it.imdbID))
                            navController.popBackStack()
                        },
                            modifier = Modifier
                                .padding(2.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                        ) {
                            Text("SİL", color = Color.Black,fontWeight = FontWeight.ExtraBold)
                        }
                    }

              /*      if (state.error.isNotBlank()) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colors.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                                .align(CenterHorizontally)
                        )
                    }*/


                }

            }

        }
    }

}