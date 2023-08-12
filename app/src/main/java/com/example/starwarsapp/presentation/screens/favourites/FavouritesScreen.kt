package com.example.starwarsapp.presentation.screens.favourites

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.domain.model.Character
import com.example.starwarsapp.domain.model.Planet
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.presentation.common.CharacterItem
import com.example.starwarsapp.presentation.common.Header
import com.example.starwarsapp.presentation.common.PlanetItem
import com.example.starwarsapp.presentation.common.StarshipItem

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    if (state.favourites.isEmpty()) {
        EmptyScreen()
    } else LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.favourites.filterIsInstance<Character>().isNotEmpty()) {
            stickyHeader { Header(title = "Characters", Color.Blue) }
            items(
                state.favourites.filterIsInstance<Character>()
            ) { character ->
                CharacterItem(
                    character = character,
                    true,
                    onFaveClick = {},
                    onUnfaveClick = { viewModel.unfaveCharacter(it) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        if (state.favourites.filterIsInstance<Starship>().isNotEmpty()) {
            stickyHeader { Header(title = "Starships", Color.Red) }
            items(
                state.favourites.filterIsInstance<Starship>()
            ) { starship ->
                StarshipItem(
                    starship = starship,
                    true,
                    onFaveClick = {},
                    onUnfaveClick = { viewModel.unfaveStarship(it) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        if (state.favourites.filterIsInstance<Planet>().isNotEmpty()) {
            stickyHeader { Header(title = "Planets", color = Color.Magenta) }
            items(state.favourites.filterIsInstance<Planet>()) { planet ->
                PlanetItem(
                    planet = planet,
                    true,
                    onFaveClick = {},
                    onUnfaveClick = { viewModel.unfavePlanet(it) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        if (state.films.isNotEmpty()) {
            stickyHeader { Header(title = "Films", color = Color.DarkGray) }
            items(state.films.toList()) { film ->
                FilmItem(
                    film = film,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        } else if (state.error.isNotEmpty()) {
            item { Text(text = state.error, fontSize = 24.sp, textAlign = TextAlign.Center) }
        } else if (state.isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
        item { Spacer(modifier = Modifier.height(50.dp)) }
    }
}