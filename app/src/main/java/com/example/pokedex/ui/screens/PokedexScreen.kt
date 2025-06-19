package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedex.data.PokemonService
import com.example.pokedex.ui.component.pokemontile.PokemonTileSmall
import com.example.pokedex.util.PokemonUtils
import com.example.pokedex.util.PokemonUtils.similarity

/**
 * Pokédex screen that displays a searchable grid of all available Pokémon.
 *
 * - Loads lightweight cached summaries from [PokemonService].
 * - Displays a 3-column responsive grid of Pokémon using [PokemonTileSmall].
 * - Allows live search by Pokémon name.
 * - Shows a spinner while data is being loaded.
 * - Supports fuzzy search via [PokemonUtils.similarity] to improve matching.
 */
@Composable
fun PokedexScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val allPokemonModels = remember { PokemonService.getAllModels() }
    val filteredList = if (searchQuery.isBlank()) allPokemonModels

    // Support fuzzy search to make it easier for kids to find their Pokémon.
    else allPokemonModels
        .map { it to similarity(it.name, searchQuery) }
        .filter { it.second >= 0.4 }
        .sortedByDescending { it.second }
        .map { it.first }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("PokedexScreen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filteredList, key = { it.id }) { pokemon ->
                    PokemonTileSmall(pokemon = pokemon, navController = navController)
                }
            }
        }

        if (allPokemonModels.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}