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
import co.pokeapi.pokekotlin.model.Pokemon
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
    var pokemonWithEntry by remember { mutableStateOf<Pair<Pokemon, String>?>(null) }
    val scope = rememberCoroutineScope()

    // Determine the previous route so we can decide whether to show the catch button
    val previousRoute = remember {
        navController.previousBackStackEntry?.destination?.route
    }
    val showCatchButton = previousRoute == BottomNavItem.MyPokemon.route

    // Fetch Pokémon and update catch status when ID changes
    LaunchedEffect(pokemonId) {
        isCaught = MyPokemonManager.isInMyPokemon(context, pokemonId)
        pokemonWithEntry = PokemonService.getPokemonWithEntry(pokemonId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("PokemonDetailScreen")
    ) {

        if (pokemonWithEntry != null) {
            val pokemon = pokemonWithEntry!!.first
            val entry = pokemonWithEntry!!.second

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PokemonCard(
                    pokemon = pokemon,
                    entry = entry,
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
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}