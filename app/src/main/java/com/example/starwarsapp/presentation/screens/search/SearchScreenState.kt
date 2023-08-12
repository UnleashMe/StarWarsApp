package com.example.starwarsapp.presentation.screens.search

import com.example.starwarsapp.domain.model.SearchSubject

data class SearchScreenState(
    val searchInput: String = "",
    val foundCharacters: List<SearchSubject> = listOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val favourites: Set<String> = setOf()
)
