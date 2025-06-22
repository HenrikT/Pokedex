package com.trandemsolutions.pokedex.ui.screens.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trandemsolutions.pokedex.ui.component.pokemontile.PokemonTileSmall

/**
 * Pokédex screen that displays a searchable grid of all available Pokémon.
 *
 * Uses [PokedexViewModel] to observe state and trigger updates.
 * Displays a 3-column grid of [PokemonTileSmall] with a fuzzy search field.
 *
 * @param navController The navigation controller used to open Pokémon details.
 * @param viewModel The ViewModel used to manage Pokédex data.
 */
@Composable
fun PokedexScreen(
    navController: NavController,
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("PokedexScreen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = viewModel.query,
                onValueChange = viewModel::onSearchQueryChanged,
                label = { Text("Search Pokémon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.filteredList, key = { it.id }) { pokemon ->
                    PokemonTileSmall(pokemon = pokemon, navController = navController)
                }
            }
        }
    }
}