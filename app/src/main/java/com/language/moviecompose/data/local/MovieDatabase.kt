package com.language.moviecompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieLocalModel::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}