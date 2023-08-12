package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.toPlanetEntity
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.repositories.StarWarsRepository

class FavePlanetUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(planet: Planet) {
        repository.favePlanet(planet.toPlanetEntity())
    }
}