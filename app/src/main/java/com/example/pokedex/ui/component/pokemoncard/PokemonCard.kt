package com.example.pokedex.ui.component

import PokemonTypeRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.PokemonDetail

/**
 * Card layout that displays the Pokémon's name, ID, and front sprite.
 *
 * Used on the home screen to visually present the currently selected Pokémon.
 * This card occupies all available vertical space above the control buttons.
 *
 * @param pokemon The [PokemonDetail] model containing ID, name, and image URL.
 */
@Composable
fun PokemonCard(pokemon: PokemonDetail, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${pokemon.name.replaceFirstChar { it.uppercase() }} #${pokemon.id}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            PokemonImage(pokemon)
        }
    }
}