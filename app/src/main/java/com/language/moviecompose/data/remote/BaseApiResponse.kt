package com.language.moviecompose.data.remote

import android.content.Context
import com.language.moviecompose.util.NetworkUtil
import com.language.moviecompose.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

open class BaseApiResponse(
    private val context: Context,
) {
    private val networkAvailable get() = NetworkUtil.isNetworkAvailable(context)

    protected fun <T> safeApiCall(call: suspend () -> Response<T>): Flow<Resource<T>> {
        return flow<Resource<T>> {
            if (networkAvailable) {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) emit(Resource.Success(body))
                } else {
                    emit(Resource.Error(message = "Film bulunamadı !"))
                }
            }else{
                emit(Resource.Error(message = "İnternet bağlantınızı kontrol ediniz"))
            }
        }
            .catch {
                emit(Resource.Error(it.message ?: "Bir hata ile karşılaşıldı."))
            }
            .onStart { emit(Resource.Loading()) }
            .flowOn(Dispatchers.IO)
    }
}