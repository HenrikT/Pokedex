package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import co.pokeapi.pokekotlin.model.Pokemon
import com.example.pokedex.data.MyPokemonManager
import com.example.pokedex.data.MyPokemonManager.isInMyPokemon
import com.example.pokedex.data.PokemonService
import com.example.pokedex.ui.component.button.DieButton
import com.example.pokedex.ui.component.button.PokeBallButton
import com.example.pokedex.ui.component.pokemoncard.PokemonCard
import com.example.pokedex.util.PokemonUtils.MAX_POKEMON_ID
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Featured screen that shows one fun Pokémon at a time.
 *
 * Lets the user discover a random Pokémon and mark it as a favorite.
 * Meant to surprise and entertain with new creatures on each visit.
 */
@Composable
fun FeaturedScreen() {
    val context = LocalContext.current
    val startNumber = 1
    val endNumber = MAX_POKEMON_ID

    // Only generate this once (on app start)
    val initialRandomId = remember { Random.nextInt(startNumber, endNumber) }
    var randomId by remember { mutableIntStateOf(initialRandomId) }
    var isCaught by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Load Pokémon + Pokédex entry on random ID change
    val pokemonWithEntryState =
        produceState<Pair<Pokemon, String>?>(initialValue = null, key1 = randomId) {
            value = PokemonService.getPokemonWithEntry(randomId)
        }

    val pokemonWithEntry = pokemonWithEntryState.value

    // Check if Pokémon is already caught when data changes
    LaunchedEffect(pokemonWithEntry?.first?.id) {
        pokemonWithEntry?.first?.id?.let { id ->
            isCaught = isInMyPokemon(context, id)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("FeaturedScreen")
    ) {

        if (pokemonWithEntry != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Show a pokémon card at the top with name, sprite, types, and pokédex entry
                PokemonCard(
                    pokemon = pokemonWithEntry.first,
                    entry = pokemonWithEntry.second,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Show a die button to reroll the random pokémon
                    DieButton {
                        randomId = Random.nextInt(startNumber, endNumber)
                    }

                    // Show a poke ball button to catch (favorite) the encountered pokémon
                    PokeBallButton(
                        isCaught = isCaught,
                        onToggleCatch = {
                            pokemonWithEntry.first.id.let { id ->
                                coroutineScope.launch {
                                    MyPokemonManager.toggleMyPokemon(context, id)
                                    isCaught = isInMyPokemon(context, id)
                                }
                            }
                        }
                    )
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}