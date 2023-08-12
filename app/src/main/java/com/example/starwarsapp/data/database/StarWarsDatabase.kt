package com.example.starwarsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.starwarsapp.data.database.entities.CharacterEntity
import com.example.starwarsapp.data.database.entities.PlanetEntity
import com.example.starwarsapp.data.database.entities.StarshipEntity

@Database(
    entities = [
        CharacterEntity::class,
        PlanetEntity::class,
        StarshipEntity::class
    ], version = 1
)
abstract class StarWarsDatabase: RoomDatabase() {

    abstract fun getDao(): StarWarsDao
}