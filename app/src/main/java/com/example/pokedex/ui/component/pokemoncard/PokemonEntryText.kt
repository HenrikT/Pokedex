package com.example.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays the Pokédex flavor text entry for a Pokémon.
 *
 * Text is styled with larger font size and padding to ensure readability against
 * the themed background.
 *
 * @param entry The Pokédex entry text to display.
 */
@Composable
fun PokemonEntryText(entry: String) {
    Text(
        text = entry,
        modifier = Modifier.padding(top = 8.dp),
        fontSize = 18.sp,
        color = Color.White
    )
}