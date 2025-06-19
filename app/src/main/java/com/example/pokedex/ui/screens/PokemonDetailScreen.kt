package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
@Composable
fun PokemonDetailScreen(
    navController: NavController,
    pokemonId: Int
) {
    val context = LocalContext.current
    var isCaught by remember { mutableStateOf(false) }
    var isShiny by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Used to decide both if we should show the catch button and whether to load shiny state
    val cameFromMyPokemon = remember {
        navController.previousBackStackEntry?.destination?.route == BottomNavItem.MyPokemon.route
    }

    val allPokemonModels = remember { PokemonService.getAllModels() }
    val pokemon = allPokemonModels.firstOrNull { it.id == pokemonId }

    LaunchedEffect(pokemonId) {
        isCaught = MyPokemonManager.isCaught(context, pokemonId)

        // Only load shiny state if coming from "My Pokémon" screen
        isShiny = if (cameFromMyPokemon) {
            MyPokemonManager.isCaughtShiny(context, pokemonId)
        } else {
            false
        }
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
            pokemon?.let {
                PokemonCard(
                    pokemon = it,
                    isShiny = isShiny,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            if (cameFromMyPokemon && pokemon != null) {
                // Only show catch button if user came from "My Pokémon" screen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PokeBallButton(
                        isCaught = isCaught,
                        onToggleCatch = {
                            val newCaughtState = !isCaught
                            isCaught = newCaughtState
                            scope.launch {
                                MyPokemonManager.toggleCaught(context, pokemonId, isShiny)
                            }
                        }
                    )
                }
            }
        }
    }
}