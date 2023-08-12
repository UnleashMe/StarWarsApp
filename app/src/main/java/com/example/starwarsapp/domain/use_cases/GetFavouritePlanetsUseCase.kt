package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritePlanetsUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(): Flow<List<PlanetEntity>> {
        return repository.getDatabasePlanets()
    }
}