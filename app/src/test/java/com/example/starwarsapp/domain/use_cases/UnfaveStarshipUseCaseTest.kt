package com.example.starwarsapp.domain.use_cases

import android.annotation.SuppressLint
import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.data.database.entities.toStarshipEntity
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UnfaveStarshipUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var unfaveStarshipUseCase: UnfaveStarshipUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        unfaveStarshipUseCase = UnfaveStarshipUseCase(repository)

        val starships = mutableListOf<StarshipEntity>()
        ('0'..'9').forEach {
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
    fun `deleting all previously added starships returns empty list`(): Unit = runBlocking {
        ('0'..'9').forEach {
            unfaveStarshipUseCase(
                Starship(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    listOf(it.digitToInt())
                )
            )
        }
        val starships = repository.getDatabaseStarships()
        Truth.assertThat(starships.first().isEmpty()).isTrue()
    }

    @SuppressLint("CheckResult")
    @Test
    fun `adding and deleting the same starship leaves us with the same amount`(): Unit =
        runBlocking {
            val amountBefore = repository.getDatabaseStarships().first().size
            val starship = Starship("name", "m", "s", "m", listOf())
            repository.faveStarship(starship.toStarshipEntity())
            Truth.assertThat(amountBefore == repository.getDatabaseStarships().last().size - 1)
            unfaveStarshipUseCase(starship)
            Truth.assertThat(amountBefore == repository.getDatabaseStarships().last().size)
        }
}