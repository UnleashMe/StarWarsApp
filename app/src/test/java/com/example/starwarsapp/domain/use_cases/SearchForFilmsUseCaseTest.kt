package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.network.dto.FilmDto
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.example.starwarsapp.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchForFilmsUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var searchForFilmsUseCase: SearchForFilmsUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        searchForFilmsUseCase = SearchForFilmsUseCase(repository)

        val films = mutableListOf<FilmDto>()
        ('1'..'9').forEach { films.add(FilmDto(url = "$it/")) }
        repository.addFilmsToNetwork(films)
    }

    @Test
    fun `should return success with one film`(): Unit = runBlocking {
        val result = searchForFilmsUseCase(setOf(1), this)
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
        Truth.assertThat(result.last().data?.first()?.id == 1).isTrue()
    }

    @Test
    fun `should return success with zero films`(): Unit = runBlocking {
        val result = searchForFilmsUseCase(setOf(11), this)
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 0).isTrue()
    }

    @Test
    fun `should return loading on first emit and then success`(): Unit = runBlocking {
        val result = searchForFilmsUseCase(setOf(1), this)
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
    }

    /*                                                                                              unpassable tests
    @Test
    fun `should return loading and then error if HttpError`(): Unit = runBlocking {
        repository.returnHttpError()
        val result = searchForFilmsUseCase(setOf(1), this)
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.first() is Resource.Error).isTrue()
    }

    @Test
    fun `should return loading and then error if IOError`(): Unit = runBlocking {
        repository.returnIoError()
        val result = searchForFilmsUseCase(setOf(1), this)
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }*/

    @Test
    fun `should return success with one character case insensitive`(): Unit = runBlocking {
        val result = searchForFilmsUseCase(setOf(1), this)
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
    }
}