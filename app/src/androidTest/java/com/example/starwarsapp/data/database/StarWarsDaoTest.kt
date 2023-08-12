package com.example.starwarsapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.starwarsapp.data.database.entities.toCharacterEntity
import com.example.starwarsapp.data.database.entities.toPlanetEntity
import com.example.starwarsapp.data.database.entities.toStarshipEntity
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Gender
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.model.Starship
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class StarWarsDaoTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StarWarsDatabase
    private lateinit var dao: StarWarsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StarWarsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun faveAndUnfaveCharacter(): Unit = runBlocking {
        Truth.assertThat(dao.getCharacters().first().isEmpty()).isTrue()
        val character =
            Character("name", Gender.Male, "3 starships", listOf(1, 4, 3)).toCharacterEntity()
        dao.faveCharacter(character)
        val result = dao.getCharacters().first()
        Truth.assertThat(result.contains(character)).isTrue()
        Truth.assertThat(result.size == 1).isTrue()
        dao.unfaveCharacter(character)
        Truth.assertThat(dao.getCharacters().first().isEmpty()).isTrue()
    }

    @Test
    fun faveAndUnfaveStarship(): Unit = runBlocking {
        Truth.assertThat(dao.getStarships().first().isEmpty()).isTrue()
        val starship = Starship(
            "name",
            "model",
            "manufacturer",
            "passengers",
            listOf(1, 2, 3)
        ).toStarshipEntity()
        dao.faveStarship(starship)
        val result = dao.getStarships().first()
        Truth.assertThat(result.contains(starship)).isTrue()
        Truth.assertThat(result.size == 1).isTrue()
        dao.unfaveStarship(starship)
        Truth.assertThat(dao.getStarships().first().isEmpty()).isTrue()
    }

    @Test
    fun faveAndUnfavePlanet(): Unit = runBlocking {
        Truth.assertThat(dao.getPlanets().first().isEmpty()).isTrue()
        val planet = Planet("name", "diameter", "population").toPlanetEntity()
        dao.favePlanet(planet)
        val result = dao.getPlanets().first()
        Truth.assertThat(result.contains(planet)).isTrue()
        Truth.assertThat(result.size == 1).isTrue()
        dao.unfavePlanet(planet)
        Truth.assertThat(dao.getPlanets().first().isEmpty()).isTrue()
    }

    @Test
    fun puttingThreeCharactersShouldReturnThree(): Unit = runBlocking {
        Truth.assertThat(dao.getCharacters().first().isEmpty()).isTrue()
        val characters = listOf(
            Character(
                "name",
                Gender.Male,
                "3 starships",
                listOf(1, 4, 3)
            ),
            Character(
                "second",
                Gender.Female,
                "strstr",
                listOf(7, 7)
            ),
            Character("third", Gender.Male, "zzz", listOf(7))
        )
        characters.forEach {
            dao.faveCharacter(it.toCharacterEntity())
        }
        val result = dao.getCharacters().first()
        Truth.assertThat(result.size == characters.size).isTrue()
    }

    @Test
    fun puttingThreeStarshipsShouldReturnThree(): Unit = runBlocking {
        Truth.assertThat(dao.getStarships().first().isEmpty()).isTrue()
        val starships = listOf(
            Starship(
                "name",
                "model",
                "manufacturer",
                "passengers",
                listOf(1)
            ),
            Starship(
                "second",
                "strstr",
                "manu chao",
                "papsengers",
                listOf(2)
            ),
            Starship("third", "momodel", "zzz", "popsingers", listOf(3))
        )
        starships.forEach {
            dao.faveStarship(it.toStarshipEntity())
        }
        val result = dao.getStarships().first()
        Truth.assertThat(result.size == starships.size).isTrue()
    }

    @Test
    fun puttingThreePlanetsShouldReturnThree(): Unit = runBlocking {
        Truth.assertThat(dao.getPlanets().first().isEmpty()).isTrue()
        val planets = listOf(
            Planet(
                "name",
                "diameter",
                "population"
            ),
            Planet(
                "second",
                "strstr",
                "popper"
            ),
            Planet("third", "zzz", "ppp")
        )
        planets.forEach {
            dao.favePlanet(it.toPlanetEntity())
        }
        val result = dao.getPlanets().first()
        Truth.assertThat(result.size == planets.size).isTrue()
    }
}