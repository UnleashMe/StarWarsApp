package com.example.starwarsapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val name: String,
    val gender: String,
    val starships: String,
    val films: String
) {
    fun toCharacter(): Character {
        return Character(
            name = name,
            gender = if (gender == "male") Gender.Male else Gender.Female,
            starships = starships,
            films = films.map { it.digitToInt() }
        )
    }
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        name = name,
        gender = if (gender is Gender.Male) "male" else "female",
        starships = starships,
        films = films.joinToString("")
    )
}