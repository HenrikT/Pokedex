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
import com.example.pokedex.data.FavoritesManager
import com.example.pokedex.data.IPokemonRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonService
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.ui.component.pokemoncard.PokemonCard
import com.example.pokedex.util.PokemonUtils.MAX_POKEMON_ID
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val startId = 1
    val endId = MAX_POKEMON_ID

    val repository: IPokemonRepository = PokemonRepository()
    val service = PokemonService(repository, FavoritesManager)

    val initialId = remember { Random.nextInt(startId, endId) }
    var currentId by remember { mutableIntStateOf(initialId) }

    val pokemonState = produceState<PokemonDetail?>(initialValue = null, key1 = currentId) {
        value = service.getPokemonDetail(currentId)
    }

    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(pokemonState.value?.id) {
        pokemonState.value?.id?.let { id ->
            isFavorite = service.isFavorite(context, id)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("HomeScreen")
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
                            service.setFavorite(context, pokemon.id, !isFavorite)
                            isFavorite = !isFavorite
                        }
                    }) {
                        val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                        Icon(imageVector = icon, contentDescription = "Toggle favorite")
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