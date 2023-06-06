package com.language.moviecompose.domain.use_case.local_useCase.insert_useCase

import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.data.remote.dto.toMovieLocal
import com.language.moviecompose.domain.model.MovieDetail
import com.language.moviecompose.domain.repository.MovieRepository
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeInsertMovie(movieDetail: MovieDetail): Flow<Resource<MovieLocalModel>> = flow {

        try {

            emit(Resource.Loading())
            val movieLocal = movieDetail.toMovieLocal()
            repository.insertMovie(movieLocal)
            emit(Resource.Success(movieLocal))

        } catch (e : Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Beklenilmeyen bir hata ile karşılaşıldı !"))
        }
    }
}
