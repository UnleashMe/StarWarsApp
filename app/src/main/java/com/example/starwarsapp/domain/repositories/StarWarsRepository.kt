package com.example.starwarsapp.domain.repositories

import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.data.network.dto.CharacterSearchResponse
import com.example.starwarsapp.data.network.dto.FilmDto
import com.example.starwarsapp.data.network.dto.PlanetSearchResponse
import com.example.starwarsapp.data.network.dto.StarshipSearchResponse
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {

    suspend fun getCharacters(text: String, page: Int): CharacterSearchResponse

    suspend fun getStarships(text: String, page: Int): StarshipSearchResponse

    suspend fun getPlanets(text: String, page: Int): PlanetSearchResponse

    suspend fun faveCharacter(characterEntity: CharacterEntity)

    suspend fun getDatabaseCharacters(): Flow<List<CharacterEntity>>

    suspend fun unfaveCharacter(characterEntity: CharacterEntity)

    suspend fun faveStarship(starshipEntity: StarshipEntity)

    suspend fun getDatabaseStarships(): Flow<List<StarshipEntity>>

    suspend fun unfaveStarship(starshipEntity: StarshipEntity)

    suspend fun favePlanet(planetEntity: PlanetEntity)

    suspend fun getDatabasePlanets(): Flow<List<PlanetEntity>>

    suspend fun unfavePlanet(planetEntity: PlanetEntity)

    suspend fun getFilmById(id: Int): FilmDto?
}