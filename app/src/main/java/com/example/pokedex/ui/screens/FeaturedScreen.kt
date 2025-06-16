package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.pokedex.data.MyPokemonManager
import com.example.pokedex.data.IPokemonRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonService
import com.example.pokedex.model.PokemonDetail
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
    val coroutineScope = rememberCoroutineScope()

    val startId = 1
    val endId = MAX_POKEMON_ID

    val repository: IPokemonRepository = PokemonRepository()
    val service = PokemonService(repository, MyPokemonManager)

    val initialId = remember { Random.nextInt(startId, endId) }
    var currentId by remember { mutableIntStateOf(initialId) }

    val pokemonState = produceState<PokemonDetail?>(initialValue = null, key1 = currentId) {
        value = service.getPokemonDetail(currentId)
    }

    var isInMyPokemon by remember { mutableStateOf(false) }

    LaunchedEffect(pokemonState.value?.id) {
        pokemonState.value?.id?.let { id ->
            isInMyPokemon = service.isInMyPokemon(context, id)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("FeaturedScreen")
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            service.setMyPokemon(context, pokemon.id, !isInMyPokemon)
                            isInMyPokemon = !isInMyPokemon
                        }
                    }) {
                        val icon = if (isInMyPokemon) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(imageVector = icon, contentDescription = "Add to my pokémon")
                    }

                    Button(onClick = {
                        currentId = Random.nextInt(startId, endId)
                    }) {
                        Text("Randomize")
                    }
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}