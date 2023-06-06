package com.language.moviecompose.presentation.screen.moviscreen.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.data.remote.dto.toMovieDetail
import com.language.moviecompose.data.repository.MovieRepositoryImpl
import com.language.moviecompose.domain.model.MovieDetail
import com.language.moviecompose.domain.use_case.local_useCase.CheckExistUseCase
import com.language.moviecompose.domain.use_case.local_useCase.delete_useCase.DeleteUseCase
import com.language.moviecompose.domain.use_case.local_useCase.insert_useCase.InsertUseCase
import com.language.moviecompose.presentation.screen.moviscreen.movies.MoviesEvent
import com.language.moviecompose.util.Constants.IMDB_ID
import com.language.moviecompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle, // viewmodellar içinde kullanılabilen rootlarda bir değer yolanıldığında ".get" ile aldığımız yapıdır SavedStateHandle
    private val getInsertMovieUseCase: InsertUseCase,
    private val getDeleteMovieUseCase: DeleteUseCase,
    private val getExistUserCase: CheckExistUseCase,
    private val apiRepository: MovieRepositoryImpl

    ) : ViewModel() {

    // remote
    private val _state = mutableStateOf<MovieDetailState>(MovieDetailState())
    val state: State<MovieDetailState> = _state

    //insert
    private val _insertState = mutableStateOf<Resource<MovieLocalModel>>(Resource.Loading())
    val insertState: State<Resource<MovieLocalModel>> = _insertState


    // delete
    private val _deleteState = mutableStateOf<Resource<Unit>>(Resource.Loading())
    val deleteState: State<Resource<Unit>> = _deleteState

    // kaydedilmiş veriler için buton görünürlüğü
    private var _isMovieSaved = MutableStateFlow(false)
    val isMovieSaved: StateFlow<Boolean> = _isMovieSaved.asStateFlow()

    // ui'da gösterilecek hata için
    private val _uiEvent: MutableSharedFlow<MoviesDetailEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()


    init {
        // Root'a yollanan id'yi aldığımız alan
        stateHandle.get<String>(IMDB_ID)?.let {
            getMovieDetail(it)
            checkExistMovie(it)
        }
    }

    private fun checkExistMovie(imdbId: String){
        getExistUserCase.executeCheckExist(id = imdbId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        checkExistMovie = it.data
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieDetail(imdbId: String) {
        viewModelScope.launch {
            apiRepository.getMovieDetail(imdbId).collect{
                when (it) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false, movie = it.data?.toMovieDetail())
                    }

                    is Resource.Error -> {
                        _uiEvent.emit(MoviesDetailEvent.SnackBarError(it.message))
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }


    }

    // apiden gelen bilgileri local'e kaydet
    fun insertMovie(movieDetail: MovieDetail) {
        getInsertMovieUseCase.executeInsertMovie(movieDetail).onEach {
            _insertState.value = it
            _isMovieSaved.value = true
        }.launchIn(viewModelScope)
    }


    // localden veri silme
    fun deleteMovie(movieLocalModel: MovieLocalModel) {
        getDeleteMovieUseCase.executeDeleteMovie(movieLocalModel).onEach {
            _deleteState.value = it
        }.launchIn(viewModelScope)
    }



}