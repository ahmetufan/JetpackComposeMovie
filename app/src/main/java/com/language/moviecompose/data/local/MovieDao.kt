package com.language.moviecompose.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieLocalModel)

    @Delete
    suspend fun deleteMovie(movie: MovieLocalModel)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieLocalModel>

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE imdbID = :imdbID)")
    suspend fun checkIfMovieExists(imdbID: String): Int


}