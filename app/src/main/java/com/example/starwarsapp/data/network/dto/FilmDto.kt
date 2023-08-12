package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Film

data class FilmDto(
    val characters: List<String> = listOf(),
    val created: String = "",
    val director: String = "",
    val edited: String = "",
    val episode_id: Int = 0,
    val opening_crawl: String = "",
    val planets: List<String> = listOf(),
    val producer: String = "",
    val release_date: String = "",
    val species: List<String> = listOf(),
    val starships: List<String> = listOf(),
    val title: String = "",
    val url: String = "",
    val vehicles: List<String> = listOf()
) {
    fun toFilm(): Film {
        return Film(
            title = title,
            director = "Director: $director",
            producer = "Producer: $producer",
            id = url.dropLast(1).last().digitToInt()
        )
    }
}