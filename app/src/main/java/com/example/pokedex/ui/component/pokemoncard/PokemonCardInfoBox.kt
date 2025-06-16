package com.example.pokedex.ui.component.pokemoncard

import PokemonTypeRow
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
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.util.PokemonUtils.PokemonCardContainerBackground

/**
 * Container displaying the Pokémon's type row and Pokédex entry with a styled background.
 *
 * Used inside [PokemonCard] to wrap type badges and flavor text in a highlighted area,
 * improving readability and visual structure.
 *
 * @param pokemon The Pokémon whose types and entry should be rendered in the info box.
 */
@Composable
fun PokemonCardInfoBox(pokemon: PokemonDetail) {
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
            PokemonEntryText(pokemon.entry)
        }
    }
}