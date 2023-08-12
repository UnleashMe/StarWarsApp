package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.database.StarWarsDao
import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.StarshipEntity
import com.example.starwarsapp.data.network.StarWarsService
import com.example.starwarsapp.data.network.dto.CharacterSearchResponse
import com.example.starwarsapp.data.network.dto.FilmDto
import com.example.starwarsapp.data.network.dto.PlanetSearchResponse
import com.example.starwarsapp.data.network.dto.StarshipSearchResponse
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(
    private val starWarsService: StarWarsService,
    private val starWarsDao: StarWarsDao
) : StarWarsRepository {
    override suspend fun getCharacters(text: String, page: Int): CharacterSearchResponse {
        return starWarsService.getCharacters(text, page)
    }

    override suspend fun getStarships(text: String, page: Int): StarshipSearchResponse {
        return starWarsService.getStarships(text, page)
    }

    override suspend fun getPlanets(text: String, page: Int): PlanetSearchResponse {
        return starWarsService.getPlanets(text, page)
    }

    override suspend fun faveCharacter(characterEntity: CharacterEntity) {
        starWarsDao.faveCharacter(characterEntity)
    }

    override suspend fun getDatabaseCharacters(): Flow<List<CharacterEntity>> {
        return starWarsDao.getCharacters()
    }

    override suspend fun unfaveCharacter(characterEntity: CharacterEntity) {
        starWarsDao.unfaveCharacter(characterEntity)
    }

    override suspend fun faveStarship(starshipEntity: StarshipEntity) {
        starWarsDao.faveStarship(starshipEntity)
    }

    override suspend fun getDatabaseStarships(): Flow<List<StarshipEntity>> {
        return starWarsDao.getStarships()
    }

    override suspend fun unfaveStarship(starshipEntity: StarshipEntity) {
        starWarsDao.unfaveStarship(starshipEntity)
    }

    override suspend fun favePlanet(planetEntity: PlanetEntity) {
        starWarsDao.favePlanet(planetEntity)
    }

    override suspend fun getDatabasePlanets(): Flow<List<PlanetEntity>> {
        return starWarsDao.getPlanets()
    }

    override suspend fun unfavePlanet(planetEntity: PlanetEntity) {
        starWarsDao.unfavePlanet(planetEntity)
    }

    override suspend fun getFilmById(id: Int): FilmDto {
        return starWarsService.getFilmById(id)
    }
}