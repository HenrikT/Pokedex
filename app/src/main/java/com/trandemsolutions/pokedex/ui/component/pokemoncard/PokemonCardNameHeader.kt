package com.trandemsolutions.pokedex.ui.component.pokemoncard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trandemsolutions.pokedex.model.PokemonModel
import com.trandemsolutions.pokedex.util.PokemonUtils
import com.trandemsolutions.pokedex.util.PokemonUtils.PokemonCardContainerBackground

/**
 * Header displaying a Pokémon's name and ID with a styled background.
 *
 * Used at the top of [PokemonCard] to clearly present the Pokémon's identity.
 *
 * @param pokemon The [PokemonModel] whose name and ID should be shown.
 */
@Composable
fun PokemonCardNameHeader(pokemon: PokemonModel, isShiny: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = PokemonCardContainerBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = PokemonUtils.getFormattedPokemonName(pokemon, isShiny),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}