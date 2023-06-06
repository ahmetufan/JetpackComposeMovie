package com.language.moviecompose.presentation.screen.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {


    val favoriteMovies = MutableLiveData<List<MovieLocalModel>>()
    val isRefreshing = MutableLiveData<Boolean>()

    fun getAllFavoriteMovie() {
        viewModelScope.launch {
            try {

                isRefreshing.postValue(true)

                val movies = repository.getAllMovies()
                favoriteMovies.postValue(movies)

                isRefreshing.postValue(false)

            } catch (e: Exception) {
                e.printStackTrace()
                isRefreshing.postValue(false)
            }
        }
    }

}