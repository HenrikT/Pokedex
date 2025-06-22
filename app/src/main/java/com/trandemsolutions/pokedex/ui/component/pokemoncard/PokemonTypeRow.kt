package com.trandemsolutions.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trandemsolutions.pokedex.model.PokemonModel

/**
 * Displays all types of a Pokémon as evenly spaced badges in a horizontal row.
 *
 * Each type is rendered using [PokemonTypeBadge] with equal weight distribution.
 *
 * @param pokemon The [PokemonModel] whose types are displayed.
 */
@Composable
fun PokemonTypeRow(pokemon: PokemonModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        pokemon.types.forEach { type ->
            PokemonTypeBadge(
                type = type,
                modifier = Modifier.weight(1f)
            )
        }
    }
}