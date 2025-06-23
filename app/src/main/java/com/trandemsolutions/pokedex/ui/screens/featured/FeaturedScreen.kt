package com.trandemsolutions.pokedex.ui.screens.featured

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trandemsolutions.pokedex.ui.component.button.DieButton
import com.trandemsolutions.pokedex.ui.component.button.PokeBallButton
import com.trandemsolutions.pokedex.ui.component.pokemoncard.PokemonCard

/**
 * Featured Pokémon screen that displays a randomly selected Pokémon.
 *
 * This screen shows a large card with Pokémon info and a button to reroll
 * a new Pokémon. It also allows the user to catch or release the featured Pokémon.
 *
 * The current Pokémon is managed via [FeaturedViewModel] and its state is updated
 * using lifecycle-aware logic through [LaunchedEffect].
 *
 * @param viewModel The ViewModel used to manage featured Pokémon state.
 */
@Composable
fun FeaturedScreen(viewModel: FeaturedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.pokemonId) {
        viewModel.loadPokemonState(context)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                uiState.pokemon?.let { pokemon ->
                    PokemonCard(
                        pokemon = pokemon,
                        isShiny = uiState.isShiny
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DieButton { viewModel.generateRandomPokemon() }

                PokeBallButton(
                    isCaught = uiState.isCaught,
                    onToggleCatch = {
                        viewModel.toggleCatch(context)
                    }
                )
            }
        }
    }
}