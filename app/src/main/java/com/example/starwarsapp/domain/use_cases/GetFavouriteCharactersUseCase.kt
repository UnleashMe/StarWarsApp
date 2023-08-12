package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteCharactersUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(): Flow<List<CharacterEntity>> {
        return repository.getDatabaseCharacters()
    }
}