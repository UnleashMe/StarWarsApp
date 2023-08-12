package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFavouriteStarshipsUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var getFavouriteStarshipsUseCase: GetFavouriteStarshipsUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        getFavouriteStarshipsUseCase = GetFavouriteStarshipsUseCase(repository)

        val starships = mutableListOf<StarshipEntity>()
        ('a'..'z').forEach {
            starships.add(
                StarshipEntity(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            )
        }
        runBlocking { starships.forEach { repository.faveStarship(it) } }
    }

    @Test
    fun `making sure that function returns the added amount of starships`() = runBlocking {
        val starships = getFavouriteStarshipsUseCase()
        Truth.assertThat(starships.first().size == ('a'..'z').count()).isTrue()
    }

    @Test
    fun `searching for some starship`(): Unit = runBlocking {
        val randomChar = ('a'..'z').random()
        val starshipEntity = StarshipEntity(
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString(),
            randomChar.toString()
        )
        Truth.assertThat(getFavouriteStarshipsUseCase().first().contains(starshipEntity)).isTrue()
    }
}