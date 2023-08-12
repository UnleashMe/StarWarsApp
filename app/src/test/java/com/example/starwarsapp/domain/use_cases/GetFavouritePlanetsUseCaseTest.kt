package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFavouritePlanetsUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var getFavouritePlanetUseCase: GetFavouritePlanetsUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        getFavouritePlanetUseCase = GetFavouritePlanetsUseCase(repository)

        val planets = mutableListOf<PlanetEntity>()
        ('a'..'z').forEach {
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
    fun `making sure that function returns the added amount of planets`() = runBlocking {
        val planets = getFavouritePlanetUseCase()
        Truth.assertThat(planets.first().size == ('a'..'z').count()).isTrue()
    }

    @Test
    fun `searching for some planet`(): Unit = runBlocking {
        val randomChar = ('a'..'z').random()
        val planetEntity = PlanetEntity(
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString()
        )
        Truth.assertThat(getFavouritePlanetUseCase().first().contains(planetEntity)).isTrue()
    }
}