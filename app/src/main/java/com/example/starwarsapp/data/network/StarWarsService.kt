package com.example.starwarsapp.data.network

import com.example.starwarsapp.data.network.dto.CharacterSearchResponse
import com.example.starwarsapp.data.network.dto.FilmDto
import com.example.starwarsapp.data.network.dto.PlanetSearchResponse
import com.example.starwarsapp.data.network.dto.StarshipSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsService {

    @GET("people")
    suspend fun getCharacters(
        @Query("search") text: String,
        @Query("page") page: Int
    ): CharacterSearchResponse

    @GET("starships")
    suspend fun getStarships(
        @Query("search") text: String,
        @Query("page") page: Int
    ): StarshipSearchResponse

    @GET("planets")
    suspend fun getPlanets(
        @Query("search") text: String,
        @Query("page") page: Int
    ): PlanetSearchResponse

    @GET("films/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int
    ): FilmDto
}