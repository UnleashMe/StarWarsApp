package com.example.starwarsapp.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    onItemClick: (Screen) -> Unit
) {
    val screens = listOf(
        Screen.SearchScreen,
        Screen.FavouritesScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach {
            BottomNavigationItem(
                selected = it.route == currentDestination?.route,
                onClick = { onItemClick(it) },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) })
        }
    }
}