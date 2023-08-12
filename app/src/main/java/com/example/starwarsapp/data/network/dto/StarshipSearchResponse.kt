package com.example.starwarsapp.data.network.dto

data class StarshipSearchResponse(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<StarshipDto> = listOf()
)