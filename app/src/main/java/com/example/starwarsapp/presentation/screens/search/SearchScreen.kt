package com.example.starwarsapp.presentation.screens.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.searchInput,
            onValueChange = {
                viewModel.onSearchInput(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            trailingIcon = {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            },
            label = {
                Text(text = "Search")
            }
        )
        if (state.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error)
            }
        }
        if (state.foundCharacters.isEmpty() && state.searchInput.length < 2) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .graphicsLayer(rotationX = 30f)
                    .clipToBounds(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A long time ago in a galaxy far, far away.... " +
                            "creatures were unable to search for their favourite Star Wars characters," +
                            " starships or planets... but now it is possible!",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else if (state.foundCharacters.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No matches found!", fontSize = 32.sp, textAlign = TextAlign.Center)
            }
        } else LazyColumn {
            if (state.foundCharacters.filterIsInstance<Character>().isNotEmpty()) {
                stickyHeader { Header(title = "Characters", Color.Blue) }
                items(
                    state.foundCharacters.filterIsInstance<Character>(),
                    key = { it.name }) { character ->
                    CharacterItem(
                        character = character,
                        character.name in state.favourites,
                        onFaveClick = { viewModel.faveCharacter(it) },
                        onUnfaveClick = { viewModel.unfaveCharacter(it) })
                }
            }
            if (state.foundCharacters.filterIsInstance<Starship>().isNotEmpty()) {
                stickyHeader { Header(title = "Starships", Color.Red) }
                items(
                    state.foundCharacters.filterIsInstance<Starship>(),
                    key = { it.name }) { starship ->
                    StarshipItem(
                        starship = starship,
                        isFaved = starship.name in state.favourites,
                        onFaveClick = { viewModel.faveStarship(it) },
                        onUnfaveClick = { viewModel.unfaveStarship(it) })
                }
            }
            if (state.foundCharacters.filterIsInstance<Planet>().isNotEmpty()) {
                stickyHeader { Header(title = "Planets", color = Color.Magenta) }
                items(state.foundCharacters.filterIsInstance<Planet>(), key = { it.name }) { planet ->
                    PlanetItem(
                        planet = planet,
                        isFaved = planet.name in state.favourites,
                        onFaveClick = { viewModel.favePlanet(it) },
                        onUnfaveClick = { viewModel.unfavePlanet(it) })
                }
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }
        }
    }
}