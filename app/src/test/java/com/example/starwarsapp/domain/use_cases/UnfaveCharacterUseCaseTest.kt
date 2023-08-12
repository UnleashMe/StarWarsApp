package com.example.starwarsapp.domain.use_cases

import android.annotation.SuppressLint
import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.toCharacterEntity
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UnfaveCharacterUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var unfaveCharacterUseCase: UnfaveCharacterUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        unfaveCharacterUseCase = UnfaveCharacterUseCase(repository)

        val characters = mutableListOf<CharacterEntity>()
        ('0'..'9').forEach {
            characters.add(
                CharacterEntity(
                    it.toString(),
                    "female",
                    it.toString(),
                    it.toString()
                )
            )
        }
        runBlocking { characters.forEach { repository.faveCharacter(it) } }
    }

    @Test
    fun `deleting all previously added characters returns empty list`(): Unit = runBlocking {
        ('0'..'9').forEach {
            unfaveCharacterUseCase(
                Character(
                    it.toString(),
                    Gender.Female,
                    it.toString(),
                    listOf(it.digitToInt())
                )
            )
        }
        val characters = repository.getDatabaseCharacters()
        Truth.assertThat(characters.first().isEmpty()).isTrue()
    }

    @SuppressLint("CheckResult")
    @Test
    fun `adding and deleting the same character leaves us with the same amount`(): Unit =
        runBlocking {
            val amountBefore = repository.getDatabaseCharacters().first().size
            val character = Character("name", Gender.Male, "s", listOf())
            repository.faveCharacter(character.toCharacterEntity())
            Truth.assertThat(amountBefore == repository.getDatabaseCharacters().last().size - 1)
            unfaveCharacterUseCase(character)
            Truth.assertThat(amountBefore == repository.getDatabaseCharacters().last().size)
        }
}