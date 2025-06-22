package com.trandemsolutions.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trandemsolutions.pokedex.model.PokemonModel
import com.trandemsolutions.pokedex.ui.component.PokemonImage
import com.trandemsolutions.pokedex.util.PokemonUtils

/**
 * Card layout that displays a Pokémon's name, ID, and front-facing sprite.
 *
 * Used on the home screen and detail views to visually present Pokémon information.
 * This card occupies all available vertical space above the control buttons or below lists.
 *
 * @param pokemon The [PokemonModel] containing simplified data for rendering.
 * @param modifier Layout modifier applied to the card.
 * @param isShiny Whether to apply a golden flashy glow to this card.
 */
@Composable
fun PokemonCard(
    pokemon: PokemonModel,
    modifier: Modifier = Modifier,
    isShiny: Boolean = false
) {
    val typeNames = pokemon.types.map { it.type.name }
    val backgroundBrush = PokemonUtils.getTypeBackground(typeNames)

    val shape = RoundedCornerShape(16.dp)

    val shinyGlow = if (isShiny) {
        Modifier
            .shadow(
                elevation = 24.dp,
                shape = shape,
                ambientColor = Color(0xFFFFD700),
                spotColor = Color(0xFFFFD700)
            )
            .border(
                width = 2.dp,
                color = Color(0xFFFFD700),
                shape = shape
            )
    } else {
        Modifier
    }

    Card(
        modifier = modifier.then(shinyGlow),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isShiny) 0.dp else 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundBrush, shape = shape)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PokemonCardNameHeader(pokemon, isShiny)
                PokemonImage(pokemon, isShiny)
                PokemonCardInfoBox(pokemon)
            }
        }
    }
}