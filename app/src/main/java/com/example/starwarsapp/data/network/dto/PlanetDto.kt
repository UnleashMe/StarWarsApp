package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Planet

data class PlanetDto(
    val climate: String = "",
    val created: String = "",
    val diameter: String = "",
    val edited: String = "",
    val films: List<String> = listOf(),
    val gravity: String = "",
    val name: String = "",
    val orbital_period: String = "",
    val population: String = "",
    val residents: List<String> = listOf(),
    val rotation_period: String = "",
    val surface_water: String = "",
    val terrain: String = "",
    val url: String = ""
) {
    fun toPlanet(): Planet {
        return Planet(
            name = name,
            diameter = "Diameter: $diameter" + if (diameter.all { it.isDigit() }) " km" else "",
            population = "Population: $population"
        )
    }
}