package com.trandemsolutions.pokedex.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.trandemsolutions.pokedex.model.PokemonModel
import com.trandemsolutions.pokedex.util.PokemonUtils

/**
 * Displays the Pokémon's front-facing sprite image.
 *
 * Uses Coil's [AsyncImage] to load the sprite from the provided URL.
 * If the sprite URL is null, no image is shown.
 *
 * @param model The [PokemonModel] containing the sprite URL and name.
 */
@Composable
fun PokemonImage(model: PokemonModel, isShiny: Boolean) {
    val spriteUrl = if (isShiny) {
        PokemonUtils.getShinyFrontSpriteUrl(model)
    } else {
        PokemonUtils.getFrontSpriteUrl(model)
    }

    spriteUrl?.let {
        AsyncImage(
            model = it,
            contentDescription = "${model.name} sprite",
            modifier = Modifier.size(256.dp)
        )
    }
}