package com.language.moviecompose.presentation.screen.moviscreen.movies

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.language.moviecompose.data.remote.dto.toMovieList
import com.language.moviecompose.data.repository.MovieRepositoryImpl
import com.language.moviecompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val apiRepository: MovieRepositoryImpl
) : ViewModel() {

    private val _state = mutableStateOf(MoviesState())
    val state : State<MoviesState> = _state

    private val _uiEvent: MutableSharedFlow<MoviesEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    // Arama metnini ViewModel'de tanımla
    var searchHint by mutableStateOf("Superman")

    init {
        getMovies(_state.value.search)
    }

    private fun getMovies(search : String){
        viewModelScope.launch {
            apiRepository.getMovies(search).collect{
                when(it) {
                    is Resource.Success -> {
                        if (it.data?.Response.equals("True")) {
                            _state.value = MoviesState(movies = it.data?.toMovieList() ?: emptyList())
                        } else {
                            _uiEvent.emit(MoviesEvent.SnackBarError("Film Bulunamadı"))
                        }

                    }

                    is Resource.Error -> {
                        _uiEvent.emit(MoviesEvent.SnackBarError(it.message))
                    }

                    is Resource.Loading -> {
                        _state.value = MoviesState(isLoading = true)
                    }

                }
            }
        }
    }





    // Herhangi bir kullanıcı etkileşime geçtiğinde çağırılacak fonksyion
    fun onEvent(event : MoviesEvent) {

        when(event) {
            is MoviesEvent.Search -> {
                searchHint = event.searchString
                getMovies(event.searchString)
            }
            else -> { Log.d("UNRESOLVED_TYPE","RegisterViewEvent unresolved type")}
        }
    }

}