package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Starship

data class StarshipDto(
    val MGLT: String = "",
    val cargo_capacity: String = "",
    val consumables: String = "",
    val cost_in_credits: String = "",
    val created: String = "",
    val crew: String = "",
    val edited: String = "",
    val films: List<String> = listOf(),
    val hyperdrive_rating: String = "",
    val length: String = "",
    val manufacturer: String = "",
    val max_atmosphering_speed: String = "",
    val model: String = "",
    val name: String = "",
    val passengers: String = "",
    val pilots: List<Any> = listOf(),
    val starship_class: String = "",
    val url: String = ""
) {
    fun toStarship(): Starship {
        return Starship(
            name = name,
            model = "Model: $model",
            manufacturer = "Manufacturer: $manufacturer",
            passengers = "Passengers: $passengers",
            films = films.map { it.dropLast(1).last().digitToInt() }
        )
    }
}