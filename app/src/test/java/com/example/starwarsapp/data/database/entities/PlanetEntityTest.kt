package com.example.starwarsapp.data.database.entities

import com.example.starwarsapp.domain.model.Planet
import com.google.common.truth.Truth
import org.junit.Test

class PlanetEntityTest {

    @Test
    fun `converting planetEntity to planet`() {
        val planetEntity = PlanetEntity(
            "n", "m", "s"
        )
        val planet = Planet("n", "m", "s")
        Truth.assertThat(planetEntity.toPlanet()).isEqualTo(planet)
    }

    @Test
    fun `conterting planet to planetEntity`() {
        val planetEntity = PlanetEntity(
            "n", "m", "s"
        )
        val planet = Planet("n", "m", "s")
        Truth.assertThat(planet.toPlanetEntity()).isEqualTo(planetEntity)
    }
}