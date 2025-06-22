package com.trandemsolutions.pokedex.ui.screens.mypokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trandemsolutions.pokedex.ui.component.pokemontile.PokemonTile

/**
 * Displays a grid of Pokémon that the user has caught ("My Pokémon").
 *
 * This screen:
 * - Observes the user's saved Pokémon via [MyPokemonViewModel].
 * - Renders them in a 2-column grid layout using [PokemonTile].
 * - Shows a spinner while loading or a fallback message if the list is empty.
 *
 * @param navController The navigation controller used to open Pokémon details.
 * @param viewModel The ViewModel backing this screen.
 */
@Composable
fun MyPokemonScreen(
    navController: NavController,
    viewModel: MyPokemonViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeCaughtPokemon(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .semantics { contentDescription = "My Pokémon screen showing saved Pokémon" }
            .testTag("MyPokemonScreen")
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.pokemonList.isEmpty() -> {
                Text(
                    text = "No Pokémon caught yet",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            else -> {
                Column {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.pokemonList,
                            key = { it.first.id }
                        ) { (pokemon, isShiny) ->
                            PokemonTile(navController, pokemon, isShiny = isShiny)
                        }
                    }
                }
            }
        }
    }
}