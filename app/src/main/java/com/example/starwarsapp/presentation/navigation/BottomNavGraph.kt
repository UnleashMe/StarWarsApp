package com.example.starwarsapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.favourites.FavouritesScreen
import com.example.starwarsapp.presentation.screens.search.SearchScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(route = Screen.SearchScreen.route) {
            SearchScreen()
        }
        composable(route = Screen.FavouritesScreen.route) {
            FavouritesScreen()
        }
    }
}