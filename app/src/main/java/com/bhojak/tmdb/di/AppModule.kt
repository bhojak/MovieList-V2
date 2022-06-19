package com.bhojak.tmdb.di

import android.content.Context
import androidx.room.Room
import com.bhojak.tmdb.data.network.ApiService
import com.bhojak.tmdb.domain.repository.MovieRepo
import com.bhojak.tmdb.domain.repository.SearchRepo
import com.bhojak.tmdb.data.room.MovieDao
import com.bhojak.tmdb.data.room.MovieDatabase
import com.bhojak.tmdb.data.util.Constants
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
    fun provideSearchRepo(apiService: ApiService) = SearchRepo(apiService)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : MovieDatabase{
        return Room.databaseBuilder(context,MovieDatabase::class.java,"movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideMovieRepo(apiService: ApiService, movieDao: MovieDao, @ApplicationContext context: Context): MovieRepo {
        return MovieRepo(apiService, movieDao, context)
    }



    @Singleton
    @Provides
    fun provideMoviesDao(movieDatabase: MovieDatabase): MovieDao{
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideApiService() : ApiService{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}