package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.toPlanetEntity
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FavePlanetUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var favePlanetUseCase: FavePlanetUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        favePlanetUseCase = FavePlanetUseCase(repository)
    }

    @Test
    fun `inserting planets then counting them`(): Unit = runBlocking {
        val planetsToFave = mutableListOf<Planet>()
        ('a'..'z').forEach { c ->
            planetsToFave.add(Planet(c.toString(), c.toString(), c.toString()))
        }
        planetsToFave.forEach {
            repository.favePlanet(it.toPlanetEntity())
        }
        Truth.assertThat(repository.getDatabasePlanets().first().size == ('a'..'z').count())
            .isTrue()
    }

    @Test
    fun `making sure the planet is added`(): Unit = runBlocking {
        val planetEntity = PlanetEntity("name", "s", "s")
        repository.favePlanet(planetEntity)
        Truth.assertThat(repository.getDatabasePlanets().first().first() == planetEntity)
            .isTrue()
    }
}