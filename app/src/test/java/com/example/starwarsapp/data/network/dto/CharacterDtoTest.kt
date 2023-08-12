package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.google.common.truth.Truth
import org.junit.Test

class CharacterDtoTest {

    @Test
    fun `converting characterDto to character`() {
        val characterDto = CharacterDto(name = "n", gender = "male", starships = listOf("a", "b", "c", "d"), films = listOf("1/", "2/", "3/"))
        val character = Character("n", Gender.Male, "Piloted 4 starships", listOf(1, 2, 3))
        Truth.assertThat(characterDto.toCharacter()).isEqualTo(character)
    }
}
