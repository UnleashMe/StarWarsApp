package com.example.starwarsapp.presentation.screens.favourites

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.use_cases.DatabaseUseCases
import com.example.starwarsapp.domain.use_cases.NetworkUseCases
import com.example.starwarsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases,
    private val networkUseCases: NetworkUseCases
) : ViewModel() {

    private val _state = mutableStateOf(FavouritesScreenState())
    val state: State<FavouritesScreenState> = _state

    init {
        viewModelScope.launch {
            val filmsD = async {
                val characters = databaseUseCases.getFavouriteCharactersUseCase().first().map {
                    it.toCharacter()
                }
                val starships = databaseUseCases.getFavouriteStarshipsUseCase().first().map {
                    it.toStarship()
                }
                val planets = databaseUseCases.getFavouritePlanetsUseCase().first().map {
                    it.toPlanet()
                }
                _state.value =
                    state.value.copy(favourites = (characters + starships + planets).toSet())
                val films = characters.flatMap { it.films } + starships.flatMap { it.films }
                films.toSet()
            }
            val films = filmsD.await()
            networkUseCases.searchForFilmsUseCase(films, viewModelScope).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(films = it.data ?: setOf(), isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    else -> {
                        _state.value =
                            state.value.copy(error = "Couldn't load films \n ${it.message}")
                    }
                }
            }
        }
    }

    fun unfavePlanet(planet: Planet) = viewModelScope.launch {
        databaseUseCases.unfavePlanetUseCase(planet)
        _state.value = state.value.copy(favourites = state.value.favourites - planet)
    }

    fun unfaveStarship(starship: Starship) = viewModelScope.launch {
        databaseUseCases.unfaveStarshipUseCase(starship)
        _state.value = state.value.copy(favourites = state.value.favourites - starship)
        starship.films.forEach { id ->
            if (!state.value.favourites.filterIsInstance<Character>()
                    .flatMap { it.films }
                    .contains(id) && !state.value.favourites.filterIsInstance<Starship>()
                    .flatMap { it.films }.contains(id)
            ) {
                _state.value =
                    state.value.copy(films = state.value.films.filterNot { it.id == id }.toSet())
            }
        }
    }

    fun unfaveCharacter(character: Character) = viewModelScope.launch {
        databaseUseCases.unfaveCharacterUseCase(character)
        _state.value =
            state.value.copy(favourites = state.value.favourites - character)
        character.films.forEach { id ->
            if (!state.value.favourites.filterIsInstance<Character>()
                    .flatMap { it.films }
                    .contains(id) && !state.value.favourites.filterIsInstance<Starship>()
                    .flatMap { it.films }.contains(id)
            ) {
                _state.value =
                    state.value.copy(films = state.value.films.filterNot { it.id == id }.toSet())
            }
        }
    }
}