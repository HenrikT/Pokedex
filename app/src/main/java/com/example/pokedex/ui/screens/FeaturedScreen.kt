package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.pokedex.data.MyPokemonManager.isCaught
import com.example.pokedex.data.MyPokemonManager.isCaughtShiny
import com.example.pokedex.data.MyPokemonManager.toggleCaught
import com.example.pokedex.data.PokemonService
import com.example.pokedex.ui.component.button.DieButton
import com.example.pokedex.ui.component.button.PokeBallButton
import com.example.pokedex.ui.component.pokemoncard.PokemonCard
import com.example.pokedex.util.PokemonUtils.MAX_POKEMON_ID
import com.example.pokedex.util.PokemonUtils.isShinyEncounter
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun FeaturedScreen() {
    val context = LocalContext.current
    val startNumber = 1
    val endNumber = MAX_POKEMON_ID

    var randomId by rememberSaveable { mutableIntStateOf(Random.nextInt(1, MAX_POKEMON_ID + 1)) }

    var isShinyMap by remember { mutableStateOf(mapOf<Int, Boolean>()) }
    var isCaught by remember { mutableStateOf(false) }
    var isShiny by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val allPokemonModels = remember { PokemonService.getAllModels() }
    val pokemon = allPokemonModels.firstOrNull { it.id == randomId }

    LaunchedEffect(pokemon?.id) {
        pokemon?.id?.let { id ->
            isCaught = isCaught(context, id)
            isShiny = if (isCaught) {
                isCaughtShiny(context, id)
            } else {
                val shiny = isShinyMap[id] ?: isShinyEncounter().also {
                    isShinyMap = isShinyMap + (id to it)
                }
                shiny
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("FeaturedScreen")
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DieButton {
                    randomId = Random.nextInt(startNumber, endNumber)
                }

                PokeBallButton(
                    isCaught = isCaught,
                    onToggleCatch = {
                        pokemon?.let {
                            coroutineScope.launch {
                                toggleCaught(context, pokemon.id, isShiny)
                                isCaught = isCaught(context, it.id)
                            }
                        }
                    }
                )
            }
        }
    }
}