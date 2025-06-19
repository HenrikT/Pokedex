package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedex.data.MyPokemonManager
import com.example.pokedex.data.PokemonService
import com.example.pokedex.ui.component.button.PokeBallButton
import com.example.pokedex.ui.component.pokemoncard.PokemonCard
import com.example.pokedex.ui.navigation.BottomNavItem
import kotlinx.coroutines.launch

/**
 * Displays full Pokémon detail for a given Pokémon ID.
 *
 * Used by both the Pokédex and My Pokémon pages. If the user came from
 * My Pokémon, a toggle button is shown to allow removal.
 *
 * @param navController Used to determine the calling route.
 * @param pokemonId The national Pokédex ID of the Pokémon.
 */
@Composable
fun PokemonDetailScreen(
    navController: NavController,
    pokemonId: Int
) {
    val context = LocalContext.current
    var isCaught by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Determine the previous route so we can decide whether to show the catch button
    val previousRoute = remember {
        navController.previousBackStackEntry?.destination?.route
    }
    val showCatchButton = previousRoute == BottomNavItem.MyPokemon.route

    // Load Pokémon + Pokédex entry on random ID change
    val allPokemonModels = remember { PokemonService.getAllModels() }
    val pokemon = allPokemonModels[pokemonId]

    // Fetch Pokémon and update catch status when ID changes
    LaunchedEffect(pokemonId) {
        isCaught = MyPokemonManager.isInMyPokemon(context, pokemonId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("PokemonDetailScreen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonCard(
                pokemon = pokemon,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            if (showCatchButton) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PokeBallButton(
                        isCaught = isCaught,
                        onToggleCatch = {
                            isCaught = !isCaught
                            scope.launch {
                                MyPokemonManager.toggleMyPokemon(context, pokemonId)
                            }
                        }
                    )
                }
            }
        }
    }
}