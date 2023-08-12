package com.example.starwarsapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object SearchScreen :
        Screen(route = "search_screen", title = "Search", icon = Icons.Default.Search)

    object FavouritesScreen :
        Screen(route = "favourites_screen", title = "Favourites", icon = Icons.Default.Favorite)
}
