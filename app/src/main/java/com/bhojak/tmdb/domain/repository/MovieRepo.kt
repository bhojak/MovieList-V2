package com.bhojak.tmdb.domain.repository

import android.content.Context
import android.util.Log
import com.bhojak.tmdb.domain.model.model.Credits
import com.bhojak.tmdb.domain.model.model.FullMovieDetails
import com.bhojak.tmdb.domain.model.model.KeywordsList
import com.bhojak.tmdb.domain.model.model.MoviesList
import com.bhojak.tmdb.data.network.ApiService
import com.bhojak.tmdb.data.room.FullMovieCache
import com.bhojak.tmdb.data.room.MovieDao
import com.bhojak.tmdb.data.room.MovieDetails
import com.bhojak.tmdb.data.util.DataOrException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class MovieRepo @Inject constructor(private val apiService: ApiService, private val movieDao: MovieDao, private val context: Context) {
    private val doe = DataOrException<FullMovieDetails,Boolean,Exception>()
    private val cdoe = DataOrException<Credits,Boolean,Exception>()
    private val kdoe = DataOrException<KeywordsList,Boolean,Exception>()
    private val rdoe = DataOrException<MoviesList,Boolean,Exception>()
    private val t1doe = DataOrException<MoviesList,Boolean,Exception>()
    private val t2doe = DataOrException<MoviesList,Boolean,Exception>()
    private val t3doe = DataOrException<MoviesList,Boolean,Exception>()
    private val t4doe = DataOrException<MoviesList,Boolean,Exception>()
    

    suspend fun getMovieDetails(id: Int) : DataOrException<FullMovieDetails,Boolean,Exception>{
        try{
            doe.e = null
            doe.loading = true
            doe.data = apiService.getMovieDetails(id)
            if(doe.data!=null){
                doe.loading = false
            }
        }catch (e:Exception){
            doe.e = e
        }
        doe.loading = false
        return doe
    }

    fun getContext(): Context{
        return context
    }

    suspend fun getCredits(id: Int) : DataOrException<Credits,Boolean,Exception>{
        try{
            cdoe.e = null
            cdoe.loading = true
            cdoe.data = apiService.getMovieCredits(id)
            if(cdoe.data!=null){
                cdoe.loading = false
            }
        }catch (e:Exception){
            cdoe.e = e
        }
        cdoe.loading = false
        return cdoe
    }

    suspend fun getKeywords(id: Int) : DataOrException<KeywordsList,Boolean,Exception>{
        try{
            kdoe.e = null
            kdoe.loading = true
            kdoe.data = apiService.getMovieKeywords(id)
            if(kdoe.data!=null){
                kdoe.loading = false
            }
        }catch (e:Exception){
            kdoe.e = e
        }
        kdoe.loading = false
        return kdoe
    }

    suspend fun getRecommendations(id: Int) : DataOrException<MoviesList,Boolean,Exception>{
        try{
            rdoe.e = null
            rdoe.loading = true
            rdoe.data = apiService.getMovieRecommendations(id)
            if(rdoe.data!=null){
                rdoe.loading = false
            }
        }catch (e:Exception){
            rdoe.e = e
        }
        rdoe.loading = false
        return rdoe
    }

    suspend fun getPopularMovies() : DataOrException<MoviesList,Boolean,Exception>{
        try{
            t1doe.e = null
            t1doe.loading = true
            t1doe.data = apiService.getPopularMovies()
            if(t1doe.data!=null){
                t1doe.loading = false
            }
        }catch (e:Exception){
            t1doe.e = e
            Log.d("MovieRepo GetMoviesByCategory",e.message?:"")
        }
        t1doe.loading = false
        return t1doe
    }

    suspend fun getTopRatedMovies() : DataOrException<MoviesList,Boolean,Exception>{
        try{
            t2doe.e = null
            t2doe.loading = true
            t2doe.data = apiService.getTopRatedMovies()
            if(t2doe.data!=null){
                t2doe.loading = false
            }
        }catch (e:Exception){
            t2doe.e = e
            Log.d("MovieRepo GetMoviesByCategory",e.message?:"")
        }
        t2doe.loading = false
        return t2doe
    }

    suspend fun getNowPlayingMovies() : DataOrException<MoviesList,Boolean,Exception>{
        try{
            t3doe.e = null
            t3doe.loading = true
            t3doe.data = apiService.getNowPlayingMovies()
            if(t3doe.data!=null){
                t3doe.loading = false
            }
        }catch (e:Exception){
            t3doe.e = e
            Log.d("MovieRepo GetMoviesByCategory",e.message?:"")
        }
        t3doe.loading = false
        return t3doe
    }

    suspend fun getUpcomingMovies() : DataOrException<MoviesList,Boolean,Exception>{
        try{
            t4doe.e = null
            t4doe.loading = true
            t4doe.data = apiService.getUpcomingMovies()
            if(t4doe.data!=null){
                t4doe.loading = false
            }
        }catch (e:Exception){
            t4doe.e = e
            Log.d("MovieRepo GetMoviesByCategory",e.message?:"")
        }
        t4doe.loading = false
        return t4doe
    }

    suspend fun clearMovieCacheForId(id: Int){
        movieDao.clearMovieCacheForId(id)
        Log.e("MovieRepo","DB Cleared")
    }

    suspend fun clearMovieDB(){
        movieDao.clearMovieDB()
    }

    fun getPopularMoviesFromRoom(): Flow<List<MovieDetails>>{
        return movieDao.getPopularMoviesFromRoom().flowOn(Dispatchers.IO).conflate()
    }

    fun getTopRatedMoviesFromRoom(): Flow<List<MovieDetails>>{
        return movieDao.getTopRatedMoviesFromRoom().flowOn(Dispatchers.IO).conflate()
    }

    fun getNowPlayingMoviesFromRoom(): Flow<List<MovieDetails>>{
        return movieDao.getNowPlayingMoviesFromRoom().flowOn(Dispatchers.IO).conflate()
    }

    fun getUpcomingMoviesFromRoom(): Flow<List<MovieDetails>>{
        return movieDao.getUpcomingMoviesFromRoom().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun addMovieToRoom(movieDetails: MovieDetails): Long{
        return movieDao.addMovie(movieDetails)
    }

    suspend fun addFullMovieToRoom(fullMovieCache: FullMovieCache){
        movieDao.addFullMovieCache(fullMovieCache)
        Log.e("MovieRepo", "Movie Added to Room")
    }

    fun getMovieCacheFromRoom(id: Int): Flow<String>{
        return movieDao.getMovieCacheFromRoom(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getKeywordsCacheFromRoom(id: Int): Flow<String>{
        return movieDao.getKeywordsCacheFromRoom(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getCreditsCacheFromRoom(id: Int): Flow<String>{
        return movieDao.getCreditsCacheFromRoom(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getRecommendationsCacheFromRoom(id: Int): Flow<String>{
        return movieDao.getRecommendationsCacheFromRoom(id).flowOn(Dispatchers.IO).conflate()
    }

    suspend fun updatePopular(id: Int){
        movieDao.updatePopular(id)
    }


    suspend fun updateTopRated(id: Int){
        movieDao.updateTopRated(id)
    }

    suspend fun updateNowPlaying(id: Int){
        movieDao.updateNowPlaying(id)
    }

    suspend fun updateUpcoming(id: Int){
        movieDao.updateUpcoming(id)
    }

    suspend fun getMovieById(id: Int) : MovieDetails{
        return movieDao.getMovieById(id)
    }

}