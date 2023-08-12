package com.example.starwarsapp.domain.model

data class Character(
    val name: String,
    val gender: Gender,
    val starships: String,
    val films: List<Int>
): SearchSubject()