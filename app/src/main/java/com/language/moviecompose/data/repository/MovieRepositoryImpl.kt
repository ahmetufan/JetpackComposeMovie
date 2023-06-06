package com.language.moviecompose.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.language.moviecompose.data.local.MovieDao
import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.data.remote.BaseApiResponse
import com.language.moviecompose.data.remote.MovieAPI
import com.language.moviecompose.data.remote.dto.MovieDetailDto
import com.language.moviecompose.data.remote.dto.MoviesDto
import com.language.moviecompose.domain.repository.MovieRepository
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    context: Context,
    private val api: MovieAPI,
    private val movieDao: MovieDao
) : MovieRepository, BaseApiResponse(context) {

    //remote
    override suspend fun getMovies(search: String): Flow<Resource<MoviesDto>> {
        return safeApiCall { api.getMovies(searchString = search) }
    }

    override suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetailDto>> {
        return safeApiCall { api.getMovieDetail(imdbId = imdbId) }
    }

    //local
    override suspend fun insertMovie(movieLocalModel: MovieLocalModel) {
        movieDao.insertMovie(movieLocalModel)
    }

    override suspend fun deleteMovie(movieLocalModel: MovieLocalModel) {
        movieDao.deleteMovie(movieLocalModel)
    }

    override suspend fun getAllMovies(): List<MovieLocalModel> {
        return movieDao.getAllMovies()
    }

    override suspend fun checkIfMovieExists(imdbId: String): Int {
        return movieDao.checkIfMovieExists(imdbId)
    }


}