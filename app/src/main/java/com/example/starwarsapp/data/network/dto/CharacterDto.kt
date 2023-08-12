package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender

data class CharacterDto(
    val birth_year: String = "",
    val created: String = "",
    val edited: String = "",
    val eye_color: String = "",
    val films: List<String> = listOf(),
    val gender: String = "",
    val hair_color: String = "",
    val height: String = "",
    val homeworld: String = "",
    val mass: String = "",
    val name: String = "",
    val skin_color: String = "",
    val species: List<String> = listOf(),
    val starships: List<String> = listOf(),
    val url: String = "",
    val vehicles: List<String> = listOf()
) {
    fun toCharacter(): Character {
        return Character(
            name = name,
            gender = if (gender == "male") Gender.Male else Gender.Female,
            starships = "Piloted ${starships.size} starships",
            films = films.map { it.dropLast(1).last().digitToInt() }
        )
    }
}