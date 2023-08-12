package com.example.starwarsapp.data.database.entities

import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.google.common.truth.Truth
import org.junit.Test

class CharacterEntityTest {

    @Test
    fun `converting characterEntity to character`() {
        val characterEntity = CharacterEntity(
            "n", "male", "s", "1"
        )
        val character = Character("n", Gender.Male, "s", listOf(1))
        Truth.assertThat(characterEntity.toCharacter()).isEqualTo(character)
    }

    @Test
    fun `conterting character to characterEntity`() {
        val characterEntity = CharacterEntity(
            "n", "male", "s", "1"
        )
        val character = Character("n", Gender.Male, "s", listOf(1))
        Truth.assertThat(character.toCharacterEntity()).isEqualTo(characterEntity)
    }
}