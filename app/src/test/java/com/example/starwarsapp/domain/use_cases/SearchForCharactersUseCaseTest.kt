package com.example.starwarsapp.domain.use_cases

import com.example.starwarsapp.data.network.dto.CharacterDto
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.example.starwarsapp.domain.repositories.FakeStarWarsRepository
import com.example.starwarsapp.util.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchForCharactersUseCaseTest {

    private lateinit var repository: FakeStarWarsRepository
    private lateinit var searchForCharactersUseCase: SearchForCharactersUseCase

    @Before
    fun start() {
        repository = FakeStarWarsRepository()
        searchForCharactersUseCase = SearchForCharactersUseCase(repository)

        val characters = mutableListOf<CharacterDto>()
        ('a'..'z').forEach { characters.add(CharacterDto(name = it.toString())) }
        repository.addCharactersToNetwork(characters)
    }

    @Test
    fun `should return success with one character`(): Unit = runBlocking {
        val result = searchForCharactersUseCase("a")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
        Truth.assertThat(
            result.last().data?.first() == Character(
                name = "a",
                gender = Gender.Female,
                starships = "",
                films = listOf()
            )
        )
    }

    @Test
    fun `should return success with zero characters`(): Unit = runBlocking {
        val result = searchForCharactersUseCase("aaa")
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 0).isTrue()
    }

    @Test
    fun `should return loading on first emit and then success`(): Unit = runBlocking {
        val result = searchForCharactersUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
    }

    @Test
    fun `should return loading and then error if HttpError`(): Unit = runBlocking {
        repository.returnHttpError()
        val result = searchForCharactersUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return loading and then error if IOError`(): Unit = runBlocking {
        repository.returnIoError()
        val result = searchForCharactersUseCase("a")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Error).isTrue()
    }

    @Test
    fun `should return success with one character case insensitive`(): Unit = runBlocking {
        val result = searchForCharactersUseCase("A")
        Truth.assertThat(result.first() is Resource.Loading).isTrue()
        Truth.assertThat(result.last() is Resource.Success).isTrue()
        Truth.assertThat(result.last().data?.size == 1).isTrue()
    }
}