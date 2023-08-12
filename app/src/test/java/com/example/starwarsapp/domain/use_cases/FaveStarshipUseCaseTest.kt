package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.data.database.entities.toStarshipEntity
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FaveStarshipUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var faveStarshipUseCase: FaveStarshipUseCase

    @Before
    fun setup() {
        repository = FakeStarWarsRepository()
        faveStarshipUseCase = FaveStarshipUseCase(repository)
    }

    @Test
    fun `inserting starships then counting them`(): Unit = runBlocking {
        val starshipsToFave = mutableListOf<Starship>()
        ('a'..'z').forEach { c ->
            starshipsToFave.add(
                Starship(
                    c.toString(),
                    c.toString(),
                    c.toString(),
                    c.toString(),
                    listOf()
                )
            )
        }
        starshipsToFave.forEach {
            repository.faveStarship(it.toStarshipEntity())
        }
        Truth.assertThat(repository.getDatabaseStarships().first().size == ('a'..'z').count())
            .isTrue()
    }

    @Test
    fun `making sure the starship is added`(): Unit = runBlocking {
        val starshipEntity = StarshipEntity("name", "s", "s", "s", "s")
        repository.faveStarship(starshipEntity)
        Truth.assertThat(repository.getDatabaseStarships().first().first() == starshipEntity)
            .isTrue()
    }
}