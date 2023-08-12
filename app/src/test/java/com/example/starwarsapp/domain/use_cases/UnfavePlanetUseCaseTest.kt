package com.example.starwarsapp.domain.use_cases

import android.annotation.SuppressLint
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.toPlanetEntity
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UnfavePlanetUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var unfavePlanetUseCase: UnfavePlanetUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        unfavePlanetUseCase = UnfavePlanetUseCase(repository)

        val planets = mutableListOf<PlanetEntity>()
        ('0'..'9').forEach {
            planets.add(
                PlanetEntity(
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
        }
        runBlocking { planets.forEach { repository.favePlanet(it) } }
    }

    @Test
    fun `deleting all previously added planets returns empty list`(): Unit = runBlocking {
        ('0'..'9').forEach {
            unfavePlanetUseCase(
                Planet(
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
        }
        val planets = repository.getDatabasePlanets()
        Truth.assertThat(planets.first().isEmpty()).isTrue()
    }

    @SuppressLint("CheckResult")
    @Test
    fun `adding and deleting the same planet leaves us with the same amount`(): Unit =
        runBlocking {
            val amountBefore = repository.getDatabasePlanets().first().size
            val planet = Planet("name", "m", "s")
            repository.favePlanet(planet.toPlanetEntity())
            Truth.assertThat(amountBefore == repository.getDatabasePlanets().last().size - 1)
            unfavePlanetUseCase(planet)
            Truth.assertThat(amountBefore == repository.getDatabasePlanets().last().size)
        }
}