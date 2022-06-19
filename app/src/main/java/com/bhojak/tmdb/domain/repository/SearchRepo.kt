package com.bhojak.tmdb.domain.repository

import android.util.Log
import com.bhojak.tmdb.domain.model.model.MoviesList
import com.bhojak.tmdb.data.network.ApiService
import com.bhojak.tmdb.data.util.DataOrException
import java.lang.Exception
import javax.inject.Inject


class SearchRepo @Inject constructor(private val apiService: ApiService) {
    private val doe: DataOrException<MoviesList?, Boolean?, Exception> = DataOrException(null, null, null)
    suspend fun getSearchMoviesList(searchKey: String): DataOrException<MoviesList?, Boolean?, Exception> {
        doe.e = null
        Log.d("SearchRepo","called")
        try{
            doe.loading = true
            doe.data = apiService.getSearchMoviesList(searchKey)
            if(doe.data.toString().isNotEmpty()){
                doe.loading = false
            }
        }catch (e: Exception){
            Log.d("SearchRepo", "error: $e")
            doe.e = e
        }
        doe.loading = false
        return doe
    }
}