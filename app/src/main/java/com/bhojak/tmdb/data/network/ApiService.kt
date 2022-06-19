package com.bhojak.tmdb.data.network

import com.bhojak.tmdb.domain.model.model.Credits
import com.bhojak.tmdb.domain.model.model.FullMovieDetails
import com.bhojak.tmdb.domain.model.model.KeywordsList
import com.bhojak.tmdb.domain.model.model.MoviesList
import com.bhojak.tmdb.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("search/movie")
    suspend fun getSearchMoviesList(
        @Query("query")
        query: String,
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id")
        id: Int,
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : FullMovieDetails

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id")
        id: Int,
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : Credits

    @GET("movie/{id}/keywords")
    suspend fun getMovieKeywords(
        @Path("id")
        id: Int,
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : KeywordsList

    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("id")
        id: Int,
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        api_key: String = Constants.API_KEY
    ) : MoviesList


}