package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Planet
import com.google.common.truth.Truth
import org.junit.Test

class PlanetDtoTest {

    @Test
    fun `converting planetDto to planet`() {
        val planetDto = PlanetDto(name = "n", diameter = "d", population = "p")
        val planet = Planet("n", "Diameter: d", "Population: p")
        Truth.assertThat(planetDto.toPlanet()).isEqualTo(planet)
    }
}