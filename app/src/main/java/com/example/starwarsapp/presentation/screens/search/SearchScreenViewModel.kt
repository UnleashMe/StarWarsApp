package com.example.starwarsapp.presentation.screens.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.model.SearchSubject
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.use_cases.DatabaseUseCases
import com.example.starwarsapp.domain.use_cases.NetworkUseCases
import com.example.starwarsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val networkUseCases: NetworkUseCases,
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state

    private var job: Job? = null

    init {
        viewModelScope.launch {
            launch {
                databaseUseCases.getFavouriteCharactersUseCase().collect { characterEntities ->
                    _state.value =
                        state.value.copy(favourites = state.value.favourites + characterEntities.map { it.name })
                }
            }
            launch {
                databaseUseCases.getFavouriteStarshipsUseCase().collect { starshipEntities ->
                    _state.value =
                        state.value.copy(favourites = state.value.favourites + starshipEntities.map { it.name })
                }
            }
            launch {
                databaseUseCases.getFavouritePlanetsUseCase().collect { planetEntities ->
                    _state.value =
                        state.value.copy(favourites = state.value.favourites + planetEntities.map { it.name })
                }
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onSearchInput(text: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(searchInput = text)
            if (text.length > 1) {
                val list = mutableListOf<SearchSubject>()
                launch {
                    networkUseCases.searchForCharactersUseCase(text).collect {
                        when (it) {
                            is Resource.Success -> {
                                list.addAll(it.data ?: listOf())
                            }

                            is Resource.Loading -> {
                                _state.value = state.value.copy(isLoading = true)
                            }

                            else -> {
                                _state.value =
                                    state.value.copy(error = it.message ?: "unexpected error")
                            }
                        }
                    }
                }.join()
                launch {
                    networkUseCases.searchForStarshipsUseCase(text).collect {
                        when (it) {
                            is Resource.Success -> {
                                list.addAll(it.data ?: listOf())
                            }

                            is Resource.Loading -> {
                                _state.value = state.value.copy(isLoading = true)
                            }

                            else -> {
                                _state.value =
                                    state.value.copy(error = it.message ?: "unexpected error")
                            }
                        }
                    }
                }.join()
                launch {
                    networkUseCases.searchForPlanetsUseCase(text).collect {
                        when (it) {
                            is Resource.Success -> {
                                list.addAll(it.data ?: listOf())
                            }

                            is Resource.Loading -> {
                                _state.value = state.value.copy(isLoading = true)
                            }

                            else -> {
                                _state.value =
                                    state.value.copy(error = it.message ?: "unexpected error")
                            }
                        }
                    }
                }.join()
                launch {
                    _state.value =
                        state.value.copy(
                            foundCharacters = list,
                            isLoading = false
                        )
                }
            } else {
                _state.value = state.value.copy(foundCharacters = listOf(), isLoading = false)
            }
        }
    }

    fun faveCharacter(character: Character) = viewModelScope.launch {
        databaseUseCases.faveCharacterUseCase(character)
    }

    fun unfaveCharacter(character: Character) = viewModelScope.launch {
        databaseUseCases.unfaveCharacterUseCase(character)
        _state.value =
            state.value.copy(favourites = state.value.favourites - character.name)
    }

    fun faveStarship(starship: Starship) = viewModelScope.launch {
        databaseUseCases.faveStarshipUseCase(starship)
    }

    fun unfaveStarship(starship: Starship) = viewModelScope.launch {
        databaseUseCases.unfaveStarshipUseCase(starship)
        _state.value = state.value.copy(favourites = state.value.favourites - starship.name)
    }

    fun favePlanet(planet: Planet) = viewModelScope.launch {
        databaseUseCases.favePlanetUseCase(planet)
    }

    fun unfavePlanet(planet: Planet) = viewModelScope.launch {
        databaseUseCases.unfavePlanetUseCase(planet)
        _state.value = state.value.copy(favourites = state.value.favourites - planet.name)
    }
}