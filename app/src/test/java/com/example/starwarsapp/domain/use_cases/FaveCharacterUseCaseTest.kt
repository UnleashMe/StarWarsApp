package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.toCharacterEntity
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FaveCharacterUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var faveCharacterUseCase: FaveCharacterUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        faveCharacterUseCase = FaveCharacterUseCase(repository)
    }

    @Test
    fun `inserting characters then counting them`(): Unit = runBlocking {
        val charactersToFave = mutableListOf<Character>()
        ('a'..'z').forEach { c ->
            charactersToFave.add(Character(c.toString(), Gender.Male, c.toString(), listOf()))
        }
        charactersToFave.forEach {
            repository.faveCharacter(it.toCharacterEntity())
        }
        Truth.assertThat(repository.getDatabaseCharacters().first().size == ('a'..'z').count())
            .isTrue()
    }

    @Test
    fun `making sure the character is added`(): Unit = runBlocking {
        val characterEntity = CharacterEntity("name", "male", "s", "123")
        repository.faveCharacter(characterEntity)
        Truth.assertThat(repository.getDatabaseCharacters().first().first() == characterEntity)
            .isTrue()
    }
}