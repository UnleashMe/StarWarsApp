package com.example.starwarsapp.data.database.entities

import com.example.starwarsapp.domain.model.Starship
import com.google.common.truth.Truth
import org.junit.Test

class StarshipEntityTest {

    @Test
    fun `converting starshipEntity to starship`() {
        val starshipEntity = StarshipEntity(
            "n", "m", "s", "1", "1"
        )
        val starship = Starship("n", "m", "s", "1", listOf(1))
        Truth.assertThat(starshipEntity.toStarship()).isEqualTo(starship)
    }

    @Test
    fun `conterting starship to starshipEntity`() {
        val starshipEntity = StarshipEntity(
            "n", "m", "s", "1", "1"
        )
        val starship = Starship("n", "m", "s", "1", listOf(1))
        Truth.assertThat(starship.toStarshipEntity()).isEqualTo(starshipEntity)
    }
}