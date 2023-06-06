package com.language.moviecompose.domain.use_case.local_useCase.delete_useCase

import com.language.moviecompose.data.local.MovieLocalModel
import com.language.moviecompose.domain.repository.MovieRepository
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeDeleteMovie(movie: MovieLocalModel): Flow<Resource<Unit>> = flow {

        try {

            emit(Resource.Loading())
            repository.deleteMovie(movie)
            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Beklenilmeyen bir hata ile karşılaşıldı !"))
        }
    }
}