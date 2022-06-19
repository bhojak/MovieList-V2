package com.bhojak.tmdb.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bhojak.tmdb.data.util.TMDBUtil
import kotlinx.coroutines.flow.*

private const val PREF_NAME = "favourite_movies"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)

class MovieDataStore(context: Context) {

    //key
    private val favourites = stringPreferencesKey("favourites")

    //to read
    private val favFlow : Flow<String> = context.dataStore.data.map {
        it[favourites]?:""
    }

    //to write
    private suspend fun updateFavourites(value: String, context: Context){
        context.dataStore.edit {
            it[favourites] = value
        }
    }

    suspend fun isPresent(id: Int?): Boolean {
        val s = favFlow.first()
        val obj = TMDBUtil.jsonToObject(s)
        return obj.idList.contains(id)
    }

    suspend fun addIdToFav(id: Int, context: Context){
        val s = favFlow.first()
        val obj = TMDBUtil.jsonToObject(s)
        val newList = obj.idList.toMutableList()
        newList.add(id)
        obj.idList = newList.toList()
        updateFavourites(TMDBUtil.objectToJson(obj), context)
    }

    suspend fun removeIdFromFav(id: Int, context: Context){
        val s = favFlow.first()
        val obj = TMDBUtil.jsonToObject(s)
        val newList = obj.idList.toMutableList()
        newList.remove(id)
        obj.idList = newList.toList()
        updateFavourites(TMDBUtil.objectToJson(obj), context)
    }

    suspend fun getFavListIds(): List<Int> {
        val s = favFlow.first()
        if(s.isEmpty()){
            return emptyList<Int>()
        }
        return TMDBUtil.jsonToObject(s).idList
    }
}