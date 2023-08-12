package com.example.starwarsapp.data.network.dto

import com.example.starwarsapp.domain.model.Film
import com.google.common.truth.Truth
import org.junit.Test

class FilmDtoTest {

    @Test
    fun `converting filmDto to film`() {
        val filmDto = FilmDto(title = "n", director = "d", producer = "p", url = "1/")
        val film = Film("n", "Director: d", "Producer: p", 1)
        Truth.assertThat(filmDto.toFilm()).isEqualTo(film)
    }
}