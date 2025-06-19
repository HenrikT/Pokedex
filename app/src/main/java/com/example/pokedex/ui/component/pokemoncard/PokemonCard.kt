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
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.ui.component.PokemonImage
import com.example.pokedex.util.PokemonUtils

/**
 * Card layout that displays a Pokémon's name, ID, and front-facing sprite.
 *
 * Used on the home screen and detail views to visually present Pokémon information.
 * This card occupies all available vertical space above the control buttons or below lists.
 *
 * @param pokemon The [PokemonModel] containing simplified data for rendering.
 * @param modifier Layout modifier applied to the card.
 */
@Composable
fun PokemonCard(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    val typeNames = pokemon.types.map { it.type.name }
    val backgroundBrush = PokemonUtils.getTypeBackground(typeNames)

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
                PokemonCardNameHeader(pokemon)
                PokemonImage(pokemon)
                PokemonCardInfoBox(pokemon)
            }
        }
    }
}