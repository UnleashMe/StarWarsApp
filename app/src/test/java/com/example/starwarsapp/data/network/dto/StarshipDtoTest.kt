package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Starship
import com.google.common.truth.Truth
import org.junit.Test

class StarshipDtoTest {

    @Test
    fun `converting starshipDto to starship`() {
        val starshipDto = StarshipDto(name = "n", model = "d", manufacturer = "p", passengers = "p", films = listOf("1/", "2/", "3/"))
        val starship = Starship("n", "Model: d", "Manufacturer: p", "Passengers: p", listOf(1, 2, 3))
        Truth.assertThat(starshipDto.toStarship()).isEqualTo(starship)
    }
}