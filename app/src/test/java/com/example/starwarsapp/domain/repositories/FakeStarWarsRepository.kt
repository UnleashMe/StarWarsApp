package com.example.starwarsapp.domain.repositories

import android.net.http.HttpException
import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.data.network.dto.CharacterDto
import com.example.starwarsapp.data.network.dto.CharacterSearchResponse
import com.example.starwarsapp.data.network.dto.FilmDto
import com.example.starwarsapp.data.network.dto.PlanetDto
import com.example.starwarsapp.data.network.dto.PlanetSearchResponse
import com.example.starwarsapp.data.network.dto.StarshipDto
import com.example.starwarsapp.data.network.dto.StarshipSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException

class FakeStarWarsRepository : StarWarsRepository {

    private val characters = mutableListOf<CharacterEntity>()
    private val charactersFlow = flowOf<List<CharacterEntity>>(characters)

    private val starships = mutableListOf<StarshipEntity>()
    private val starshipsFlow = flowOf<List<StarshipEntity>>(starships)

    private val planets = mutableListOf<PlanetEntity>()
    private val planetsFlow = flowOf<List<PlanetEntity>>(planets)

    private var networkCharacters = mutableListOf<CharacterDto>()
    private var networkStarships = mutableListOf<StarshipDto>()
    private var networkPlanets = mutableListOf<PlanetDto>()
    private var networkFilms = mutableSetOf<FilmDto>()


    private var shouldReturnHttpError = false
    private var shouldReturnIoError = false

    fun returnHttpError() {
        shouldReturnHttpError = true
    }

    fun returnIoError() {
        shouldReturnIoError = true
    }

    fun addCharactersToNetwork(characters: List<CharacterDto>) {
        networkCharacters.addAll(characters)
    }

    fun addPlanetsToNetwork(planets: List<PlanetDto>) {
        networkPlanets.addAll(planets)
    }

    fun addStarshipsToNetwork(starships: List<StarshipDto>) {
        networkStarships.addAll(starships)
    }

    fun addFilmsToNetwork(films: List<FilmDto>) {
        networkFilms.addAll(films)
    }

    override suspend fun getCharacters(text: String, page: Int): CharacterSearchResponse {
        if (shouldReturnHttpError) {
            throw HttpException(null, null)
        } else if (shouldReturnIoError) {
            throw IOException("")
        } else {
            val results =
                networkCharacters.filter { it.name.lowercase().contains(text.lowercase()) }
            val chunkedResults = results.chunked(10)
            if (results.isEmpty()) return CharacterSearchResponse()
            return CharacterSearchResponse(
                count = results.size,
                next = if (page == chunkedResults.size) null else "${page + 1}",
                previous = if (page == 1) null else "${page - 1}",
                results = chunkedResults[page - 1]
            )
        }
    }

    override suspend fun getStarships(text: String, page: Int): StarshipSearchResponse {
        if (shouldReturnHttpError) {
            throw HttpException(null, null)
        } else if (shouldReturnIoError) {
            throw IOException("")
        } else {
            val results = networkStarships.filter { it.name.lowercase().contains(text.lowercase()) }
            val chunkedResults = results.chunked(10)
            if (results.isEmpty()) return StarshipSearchResponse()
            return StarshipSearchResponse(
                count = results.size,
                next = if (page == chunkedResults.size) null else "${page + 1}",
                previous = if (page == 1) null else "${page - 1}",
                results = chunkedResults[page - 1]
            )
        }
    }

    override suspend fun getPlanets(text: String, page: Int): PlanetSearchResponse {
        if (shouldReturnHttpError) {
            throw HttpException(null, null)
        } else if (shouldReturnIoError) {
            throw IOException("")
        } else {
            val results = networkPlanets.filter { it.name.lowercase().contains(text.lowercase()) }
            val chunkedResults = results.chunked(10)
            if (results.isEmpty()) return PlanetSearchResponse()
            return PlanetSearchResponse(
                count = results.size,
                next = if (page == chunkedResults.size) null else "${page + 1}",
                previous = if (page == 1) null else "${page - 1}",
                results = chunkedResults[page - 1]
            )
        }
    }

    override suspend fun faveCharacter(characterEntity: CharacterEntity) {
        characters.add(characterEntity)
    }

    override suspend fun getDatabaseCharacters(): Flow<List<CharacterEntity>> {
        return charactersFlow
    }

    override suspend fun unfaveCharacter(characterEntity: CharacterEntity) {

        characters.remove(characterEntity)
    }

    override suspend fun faveStarship(starshipEntity: StarshipEntity) {
        starships.add(starshipEntity)
    }

    override suspend fun getDatabaseStarships(): Flow<List<StarshipEntity>> {
        return starshipsFlow
    }

    override suspend fun unfaveStarship(starshipEntity: StarshipEntity) {
        starships.remove(starshipEntity)
    }

    override suspend fun favePlanet(planetEntity: PlanetEntity) {
        planets.add(planetEntity)
    }

    override suspend fun getDatabasePlanets(): Flow<List<PlanetEntity>> {
        return planetsFlow
    }

    override suspend fun unfavePlanet(planetEntity: PlanetEntity) {
        planets.remove(planetEntity)
    }

    override suspend fun getFilmById(id: Int): FilmDto? {
        if (shouldReturnHttpError) {
            throw HttpException(null, null)
        } else if (shouldReturnIoError) {
            throw IOException("")
        } else {
            return networkFilms.find { it.url.contains(id.toString()) }
        }
    }
}