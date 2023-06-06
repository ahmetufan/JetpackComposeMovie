package com.language.moviecompose.util

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()
    class Error<T>(val message: String, data: T? = null) : Resource<T>()
    class Loading<T> : Resource<T>()
}