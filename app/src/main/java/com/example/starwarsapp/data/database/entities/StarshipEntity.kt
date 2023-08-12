package com.example.starwarsapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwarsapp.domain.model.Starship

@Entity(tableName = "starships")
data class StarshipEntity(
    @PrimaryKey
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: String,
    val films: String
) {
    fun toStarship(): Starship {
        return Starship(
            name = name,
            model = model,
            manufacturer = manufacturer,
            passengers = passengers,
            films = films.map { it.digitToInt() }
        )
    }
}

fun Starship.toStarshipEntity(): StarshipEntity {
    return StarshipEntity(
        name = name,
        model = model,
        manufacturer = manufacturer,
        passengers = passengers,
        films = films.joinToString("")
    )
}
