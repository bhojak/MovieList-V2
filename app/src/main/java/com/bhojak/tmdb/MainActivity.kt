package com.bhojak.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bhojak.tmdb.presentation.ui.navigation.AppNavigation
import com.bhojak.tmdb.presentation.ui.theme.TheMovieDBTheme
import com.bhojak.tmdb.presentation.screens.viewmodel.MovieViewModel
import com.bhojak.tmdb.presentation.screens.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val searchViewModel: SearchViewModel = viewModel()
            val movieViewModel: MovieViewModel = viewModel()
            TheMovieDBTheme {
                AppNavigation(searchViewModel, movieViewModel)
            }
        }
    }
}
