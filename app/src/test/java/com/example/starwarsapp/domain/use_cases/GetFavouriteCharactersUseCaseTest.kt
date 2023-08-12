package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFavouriteCharactersUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        getFavouriteCharactersUseCase = GetFavouriteCharactersUseCase(repository)

        val characters = mutableListOf<CharacterEntity>()
        ('a'..'z').forEach {
            characters.add(
                CharacterEntity(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
        }
        runBlocking { characters.forEach { repository.faveCharacter(it) } }
    }

    @Test
    fun `making sure that function returns the added amount of characters`() = runBlocking {
        val characters = getFavouriteCharactersUseCase()
        Truth.assertThat(characters.first().size == ('a'..'z').count()).isTrue()
    }

    @Test
    fun `searching for some character`() = runBlocking {
        val randomChar = ('a'..'z').random()
        val characterEntity = CharacterEntity(
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString()
        )
        Truth.assertThat(getFavouriteCharactersUseCase().first().contains(characterEntity)).isTrue()
    }
}