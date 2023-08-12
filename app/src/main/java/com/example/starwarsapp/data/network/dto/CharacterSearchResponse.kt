package com.example.starwarsapp.data.network.dto

data class CharacterSearchResponse(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<CharacterDto> = listOf()
)