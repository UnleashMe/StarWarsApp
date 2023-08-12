package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.network.dto.StarshipDto
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.example.starwarsapp.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchForStarshipsUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var searchForStarshipsUseCase: SearchForStarshipsUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        searchForStarshipsUseCase = SearchForStarshipsUseCase(repository)

        val starships = mutableListOf<StarshipDto>()
        ('a'..'z').forEach { starships.add(StarshipDto(name = it.toString())) }
        repository.addStarshipsToNetwork(starships)
    }

    @Test
    fun `should return success with one starship`(): Unit = runBlocking {
        val result = searchForStarshipsUseCase("a")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
        Truth.assertThat(
            result.last().data?.first() == Starship(
                name = "a",
                model = "",
                manufacturer = "",
                passengers = "",
                films = listOf()
            )
        )
    }

    @Test
    fun `should return success with zero starships`(): Unit = runBlocking {
        val result = searchForStarshipsUseCase("aaa")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 0).isTrue()
    }

    @Test
    fun `should return loading on first emit and then success`(): Unit = runBlocking {
        val result = searchForStarshipsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
    }

    @Test
    fun `should return loading and then error if HttpError`(): Unit = runBlocking {
        repository.returnHttpError()
        val result = searchForStarshipsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return loading and then error if IOError`(): Unit = runBlocking {
        repository.returnIoError()
        val result = searchForStarshipsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return success with one character case insensitive`(): Unit = runBlocking {
        val result = searchForStarshipsUseCase("A")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
    }
}