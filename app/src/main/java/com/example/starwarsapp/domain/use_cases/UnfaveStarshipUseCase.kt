package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.toStarshipEntity
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.repositories.StarWarsRepository

class UnfaveStarshipUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(starship: Starship) {
        repository.unfaveStarship(starship.toStarshipEntity())
    }
}