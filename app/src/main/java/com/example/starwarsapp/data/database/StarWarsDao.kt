package com.example.starwarsapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.StarshipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StarWarsDao {

    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Insert
    suspend fun faveCharacter(characterEntity: CharacterEntity)

    @Delete
    suspend fun unfaveCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM starships")
    fun getStarships(): Flow<List<StarshipEntity>>

    @Insert
    suspend fun faveStarship(starshipEntity: StarshipEntity)

    @Delete
    suspend fun unfaveStarship(starshipEntity: StarshipEntity)

    @Query("SELECT * FROM planets")
    fun getPlanets(): Flow<List<PlanetEntity>>

    @Insert
    suspend fun favePlanet(planetEntity: PlanetEntity)

    @Delete
    suspend fun unfavePlanet(planetEntity: PlanetEntity)
}