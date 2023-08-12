package com.example.starwarsapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwarsapp.domain.model.Planet

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey
    val name: String,
    val diameter: String,
    val population: String
) {
    fun toPlanet(): Planet {
        return Planet(
            name = name,
            diameter = diameter,
            population = population
        )
    }
}

fun Planet.toPlanetEntity(): PlanetEntity {
    return PlanetEntity(
        name = name,
        diameter = diameter,
        population = population
    )
}