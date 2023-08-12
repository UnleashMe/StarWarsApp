package com.example.starwarsapp.presentation.screens.favourites

import com.example.starwarsapp.domain.model.Film
import com.example.starwarsapp.domain.model.SearchSubject

data class FavouritesScreenState(
    val favourites: Set<SearchSubject> = setOf(),
    val isLoading: Boolean = false,
    val error: String = "",
    val films: Set<Film> = setOf()
)