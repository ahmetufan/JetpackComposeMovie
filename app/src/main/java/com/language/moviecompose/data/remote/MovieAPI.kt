package com.language.moviecompose.data.remote

import com.language.moviecompose.data.remote.dto.MovieDetailDto
import com.language.moviecompose.data.remote.dto.MoviesDto
import com.language.moviecompose.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {


    //https://www.omdbapi.com/?apikey=446663ef&s=batman      - home
    //https://www.omdbapi.com/?apikey=446663ef&i=tt0372784   - detail


    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : Response<MoviesDto>

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : Response<MovieDetailDto>
}