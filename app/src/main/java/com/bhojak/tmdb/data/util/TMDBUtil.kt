package com.bhojak.tmdb.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.bhojak.tmdb.data.datastore.FavList
import com.bhojak.tmdb.domain.model.model.*
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object TMDBUtil {
    fun dateFormatter(str: String): String{
        if(str.length<2){
            return " "
        }
        val apiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(str , apiFormat)
        val sDate = (date.month.toString().lowercase().substring(0,3) + " " + date.dayOfMonth.toString() + ", " + date.year.toString())
        return sDate.replaceFirstChar{
            it.uppercase()
        }
    }

    fun moneyFormatter(value: Int): String {
        if (value == 0) {
            return "-"
        }

        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(value)
    }

    fun getYear(releaseDate: String): String {
        return if(releaseDate.length < 2){
            " "
        } else{
            releaseDate.substring(0,4)
        }
    }

    fun getRunTime(runtime: Int): String{
        val h = runtime/60
        val m = runtime%60
        return "${h}h ${m}m"
    }

    fun getGenres(genres: List<Genre>): String {
        if(genres.isEmpty()){
            return " "
        }
        var s = ""
        for(genre in genres){
            s += ", " + genre.name
        }
        return s.substring(2)
    }

    fun getLanguage(key: String, list: List<SpokenLanguage>): String{
        if(key.isEmpty() || list.isEmpty())
            return "-"
        for(value in list){
            if(value.iso_639_1 == key)
                return value.name
        }
        return "-"
    }

    fun jsonToObject(json: String): FavList {
        if((false) or json.isEmpty()){
            return FavList(emptyList())
        }
        return Gson().fromJson(json,FavList::class.java)
    }

    fun objectToJson(obj: FavList): String{
        return Gson().toJson(obj)
    }

    fun jsonToMovieObject(json: String): FullMovieDetails {
        if((false) or json.isEmpty()){
            return FullMovieDetails()
        }
        return Gson().fromJson(json,FullMovieDetails::class.java)
    }

    fun movieObjectToJson(obj: FullMovieDetails): String{
        return Gson().toJson(obj)
    }

    fun jsonToCreditsObject(json: String): Credits {
        if((false) or json.isEmpty()){
            return Credits()
        }
        return Gson().fromJson(json,Credits::class.java)
    }

    fun creditsObjectToJson(obj: Credits): String{
        return Gson().toJson(obj)
    }

    fun jsonToKeywordsListObject(json: String): KeywordsList {
        if((false) or json.isEmpty()){
            return KeywordsList()
        }
        return Gson().fromJson(json,KeywordsList::class.java)
    }

    fun keywordsListObjectToJson(obj: KeywordsList): String{
        return Gson().toJson(obj)
    }

    fun jsonToRecommendationsObject(json: String): MoviesList {
        if((false) or json.isEmpty()){
            return MoviesList()
        }
        return Gson().fromJson(json,MoviesList::class.java)
    }

    fun recommendationsObjectToJson(obj: MoviesList): String{
        return Gson().toJson(obj)
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        }
        return false
    }


}