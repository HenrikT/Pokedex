package com.example.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.ui.component.pokemoncard.PokemonTypeBadge

/**
 * Displays all types of a Pokémon as evenly spaced badges in a horizontal row.
 *
 * Each type is rendered using [PokemonTypeBadge] with equal weight distribution.
 *
 * @param pokemon The simplified Pokémon model containing a list of type names.
 */
@Composable
fun PokemonTypeRow(pokemon: PokemonDetail) {
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