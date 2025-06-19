package com.example.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.ui.component.FlavorTextBox
import com.example.pokedex.util.PokemonUtils.PokemonCardContainerBackground

/**
 * Container displaying the Pokémon's types and English Pokédex entry.
 *
 * Used inside [PokemonCard] to show type badges and a flavor text description.
 * The card features a styled background to improve structure and readability.
 *
 * @param pokemon The [PokemonModel] providing type and ID information.
 */
@Composable
fun PokemonCardInfoBox(pokemon: PokemonModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                PokemonCardContainerBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PokemonTypeRow(pokemon)
            FlavorTextBox(pokemon.id)
        }
    }
}