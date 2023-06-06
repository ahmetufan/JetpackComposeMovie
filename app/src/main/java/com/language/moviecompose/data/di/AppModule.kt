package com.language.moviecompose.data.di

import android.content.Context
import androidx.room.Room
import com.language.moviecompose.MyApplication
import com.language.moviecompose.data.local.MovieDao
import com.language.moviecompose.data.local.MovieDatabase
import com.language.moviecompose.data.remote.MovieAPI
import com.language.moviecompose.data.repository.MovieRepositoryImpl
import com.language.moviecompose.domain.repository.MovieRepository
import com.language.moviecompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideMovieApi(): MovieAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        @ApplicationContext app: Context,
        api: MovieAPI,
        dao: MovieDao
    ): MovieRepository = MovieRepositoryImpl(api = api, movieDao = dao, context = app)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, "MovieDB")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(database: MovieDatabase) = database.movieDao()


}