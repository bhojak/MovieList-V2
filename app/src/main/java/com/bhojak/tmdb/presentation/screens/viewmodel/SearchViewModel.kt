package com.bhojak.tmdb.presentation.screens.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhojak.tmdb.domain.model.model.Result
import com.bhojak.tmdb.domain.repository.SearchRepo
import com.bhojak.tmdb.data.util.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo): ViewModel() {


    val searchList: MutableState<DataOrException<List<Result>,Boolean?,Exception>> = mutableStateOf(
        DataOrException(null,false,null)
    )

    fun getSearchMoviesList(query: String) {
        Log.d("SearchViewModel","called")
        searchList.value = DataOrException(emptyList(),true,null)
        viewModelScope.launch {
            Log.d("SearchViewModel", "saved")
            val doe = DataOrException<List<Result>,Boolean?,Exception>(null,true,null)
            try{
                searchList.value.loading = true
                doe.loading = true;
                val res = searchRepo.getSearchMoviesList(query)
                doe.data = res.data?.results
                doe.e = res.e
                if(doe.data != null){
                    doe.loading = false
                }
            }catch (e:Exception){
                doe.e = e
                Log.d("SearchViewModel","error: $e")
            }
            doe.loading = false
            searchList.value = doe
        }
        Log.d("SearchViewModel","completed")
    }
}