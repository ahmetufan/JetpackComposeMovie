package com.language.moviecompose.domain.repository

import androidx.lifecycle.LiveData
import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.data.remote.dto.MovieDetailDto
import com.language.moviecompose.data.remote.dto.MoviesDto
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    // remote
    suspend fun getMovies(search: String): Flow<Resource<MoviesDto>>
    suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetailDto>>

    //local
    suspend fun insertMovie(movieLocalModel: MovieLocalModel)
    suspend fun deleteMovie(movieLocalModel: MovieLocalModel)
    suspend fun  getAllMovies(): List<MovieLocalModel>
    suspend fun checkIfMovieExists(imdbId: String) : Int
}