package com.language.moviecompose.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.language.moviecompose.R
import com.language.moviecompose.presentation.screen.favorite.views.FavoriteScreen
import com.language.moviecompose.presentation.screen.moviscreen.movie_detail.views.MovieDetailScreen
import com.language.moviecompose.presentation.screen.moviscreen.movies.views.MovieScreen
import com.language.moviecompose.util.Constants


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = Destination.MovieScreen.route
    ) {

        composable(route = Destination.MovieScreen.route) {
            MovieScreen(navController = navController)
        }

        composable(route = Destination.MovieDetailScreen.route + "/{${Constants.IMDB_ID}}")  {
            MovieDetailScreen(navController = navController)
        }

        composable(route = Destination.FavoriteScreen.route)  {
            FavoriteScreen(navController = navController)
        }

    }
}


@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val bottomList = listOf(
        BottomNavItem(
            name = "MovieScreen",
            route = Destination.MovieScreen.route,
            icon = R.drawable.bottom_homepage,
            color = Color.Black
        ),

        BottomNavItem(
            name = "FavoriteScreen",
            route = Destination.FavoriteScreen.route,
            icon = R.drawable.bottom_exercise,
            color = Color.Black
        ),

    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        bottomList.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = if (selected) item.color else Color.LightGray,
                unselectedContentColor = Color.Gray,
                onClick = {
                    // Popup backstack
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.name,
                            modifier = Modifier.size(28.dp)
                        )


                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            )
        }
    }
}