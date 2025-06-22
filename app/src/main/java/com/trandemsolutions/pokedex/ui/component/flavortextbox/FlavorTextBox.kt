package com.trandemsolutions.pokedex.ui.component.flavortextbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Displays the English Pokédex entry for a given Pokémon ID.
 *
 * Uses [FlavorTextViewModel] to load flavor text via Hilt-injected service.
 *
 * @param pokemonId The ID of the Pokémon to load flavor text for.
 */
@Composable
fun FlavorTextBox(
    pokemonId: Int,
    viewModel: FlavorTextViewModel = hiltViewModel()
) {
    val flavorText by viewModel.flavorText.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadFlavorText(pokemonId)
    }

    if (flavorText == null) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Text(
            text = flavorText!!,
            color = Color.White
        )
    }
}