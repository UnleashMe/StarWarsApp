package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.toCharacterEntity
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.repositories.StarWarsRepository

class FaveCharacterUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.faveCharacter(character.toCharacterEntity())
    }
}