package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteStarshipsUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(): Flow<List<StarshipEntity>> {
        return repository.getDatabaseStarships()
    }
}