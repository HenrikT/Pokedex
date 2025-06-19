package com.example.pokedex.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import com.example.pokedex.data.PokemonService
import com.example.pokedex.util.PokemonUtils.getEnglishFlavorText

/**
 * Displays the English Pokédex entry for a given Pokémon ID.
 *
 * Shows a loading spinner while fetching species data and renders
 * the cleaned flavor text once available.
 *
 * @param pokemonId The ID of the Pokémon to load flavor text for.
 */
@Composable
fun FlavorTextBox(pokemonId: Int) {
    var flavorText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(pokemonId) {
        val species = PokemonService.getSpecies(pokemonId)
        flavorText = species?.let { getEnglishFlavorText(it.flavorTextEntries) }
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