package com.example.pokedex.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedex.data.MyPokemonManager
import com.example.pokedex.data.PokemonService
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.ui.component.pokemontile.PokemonTile

/**
 * Displays a grid of Pokémon that the user has caught ("My Pokémon").
 *
 * This screen:
 * - Observes the user's saved Pokémon via a flow.
 * - Loads each corresponding [PokemonModel] object using cached detail data.
 * - Renders them in a 2-column grid layout using [PokemonTile].
 * - Shows a spinner while loading or a fallback message if the list is empty.
 */
@Composable
fun MyPokemonScreen(navController: NavController) {
    val context = LocalContext.current

    var myPokemon by remember { mutableStateOf<Set<String>>(emptySet()) }
    var pokemonList by remember { mutableStateOf<List<PokemonModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Load and watch the user's caught Pokémon
    LaunchedEffect(Unit) {
        MyPokemonManager.getCaughtPokemon(context).collect { idSet ->
            myPokemon = idSet

            if (idSet.isNotEmpty()) {
                val fetched = idSet.mapNotNull { idStr ->
                    PokemonService.getModel(idStr.toInt())
                }
                pokemonList = fetched
            } else {
                pokemonList = emptyList()
            }

            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .semantics { contentDescription = "My Pokémon screen showing saved Pokémon" }
            .testTag("MyPokemonScreen")
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            pokemonList.isEmpty() -> {
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
                        items(pokemonList) { pokemon ->
                            PokemonTile(navController, pokemon)
                        }
                    }
                }
            }
        }
    }
}