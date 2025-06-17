package com.example.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import co.pokeapi.pokekotlin.model.Pokemon
import com.example.pokedex.ui.component.PokemonImage
import com.example.pokedex.util.PokemonUtils

/**
 * Card layout that displays the Pokémon's name, ID, and front sprite.
 *
 * Used on the home screen to visually present the currently selected Pokémon.
 * This card occupies all available vertical space above the control buttons.
 *
 * @param pokemon The [Pokemon] model containing ID, name, and image URL.
 */
@Composable
fun PokemonCard(pokemon: Pokemon, entry: String, modifier: Modifier) {

    // Create a gradient to be used for the background using the colors of the types
    // If only one type, use the color directly
    val types = pokemon.types.map { it.type.name }
    val backgroundBrush = when (types.size) {
        2 -> Brush.linearGradient(
            colors = listOf(
                PokemonUtils.getTypeColor(types[0]),
                PokemonUtils.getTypeColor(types[1])
            ),
            start = Offset(0f, 0f),
            end = Offset(1000f, 1000f)
        )
        else -> Brush.verticalGradient(
            colors = List(2) { PokemonUtils.getTypeColor(types.firstOrNull() ?: "") }
        )
    }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Show the name and number of the pokémon
                PokemonCardNameHeader(pokemon)

                // Show the pokémon image
                PokemonImage(pokemon)

                // Show the info box
                PokemonCardInfoBox(pokemon, entry)
            }
        }
    }
}