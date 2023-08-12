package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.network.dto.PlanetDto
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.example.starwarsapp.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchForPlanetsUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var searchForPlanetsUseCase: SearchForPlanetsUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        searchForPlanetsUseCase = SearchForPlanetsUseCase(repository)

        val planets = mutableListOf<PlanetDto>()
        ('a'..'z').forEach { planets.add(PlanetDto(name = it.toString())) }
        repository.addPlanetsToNetwork(planets)
    }

    @Test
    fun `should return success with one planet`(): Unit = runBlocking {
        val result = searchForPlanetsUseCase("a")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
        Truth.assertThat(result.last().data?.first() == Planet(name = "a", diameter = "", population = ""))
    }

    @Test
    fun `should return success with zero planets`(): Unit = runBlocking {
        val result = searchForPlanetsUseCase("aaa")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 0).isTrue()
    }

    @Test
    fun `should return loading on first emit and then success`(): Unit = runBlocking {
        val result = searchForPlanetsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
    }

    @Test
    fun `should return loading and then error if HttpError`(): Unit = runBlocking {
        repository.returnHttpError()
        val result = searchForPlanetsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return loading and then error if IOError`(): Unit = runBlocking {
        repository.returnIoError()
        val result = searchForPlanetsUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return success with one character case insensitive`(): Unit = runBlocking {
        val result = searchForPlanetsUseCase("A")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
    }
}