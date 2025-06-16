package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedex.data.MyPokemonManager
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonService
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.ui.component.PokeBallButton
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
    val coroutineScope = rememberCoroutineScope()

    val repository = remember { PokemonRepository() }
    val service = remember { PokemonService(repository, MyPokemonManager) }

    var isCaught by remember { mutableStateOf(false) }

    val showCatchButton = remember {
        navController.previousBackStackEntry?.destination?.route == BottomNavItem.MyPokemon.route
    }

    val pokemonState = produceState<PokemonDetail?>(initialValue = null, key1 = pokemonId) {
        value = service.getPokemonDetail(pokemonId)
    }

    LaunchedEffect(pokemonState.value?.id) {
        if (showCatchButton) {
            pokemonState.value?.id?.let { id ->
                isCaught = service.isInMyPokemon(context, id)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("PokemonDetailScreen")
    ) {
        val pokemon = pokemonState.value

        if (pokemon != null) {
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
                                coroutineScope.launch {
                                    MyPokemonManager.toggleMyPokemon(context, pokemon.id)
                                    isCaught = MyPokemonManager.isInMyPokemon(context, pokemon.id)
                                }
                            }
                        )
                    }
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}