package com.example.starwarsapp.domain.model

data class Starship(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: String,
    val films: List<Int>
): SearchSubject()
