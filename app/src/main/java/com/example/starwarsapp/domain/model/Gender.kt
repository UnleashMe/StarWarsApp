package com.example.starwarsapp.domain.model

sealed class Gender {
    object Male : Gender()
    object Female: Gender()
}