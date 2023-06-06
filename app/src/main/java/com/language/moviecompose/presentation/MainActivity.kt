package com.language.moviecompose.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.language.moviecompose.presentation.navigation.BottomNavItem
import com.language.moviecompose.presentation.navigation.BottomNavigationBar
import com.language.moviecompose.presentation.navigation.Destination
import com.language.moviecompose.presentation.navigation.Navigation
import com.language.moviecompose.presentation.ui.theme.MovieComposeTheme
import com.language.moviecompose.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    Scaffold{

                        Navigation(navController = navController)
                    }


                }
            }
        }
    }
}
