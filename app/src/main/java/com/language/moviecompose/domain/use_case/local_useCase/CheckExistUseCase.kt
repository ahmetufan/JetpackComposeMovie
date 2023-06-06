package com.language.moviecompose.domain.use_case.local_useCase

import com.language.moviecompose.domain.repository.MovieRepository
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckExistUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeCheckExist(id : String): Flow<Resource<Int>> = flow {

        try {

            emit(Resource.Loading())
            val result = repository.checkIfMovieExists(id)
            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Beklenilmeyen bir hata ile karşılaşıldı !"))
        }
    }
}