package com.example.pokedex.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.util.PokemonUtils

/**
 * Displays the Pokémon's front-facing sprite image.
 *
 * Uses Coil's [AsyncImage] to load the sprite from the provided URL.
 * If the sprite URL is null, no image is shown.
 *
 * @param model The [PokemonModel] containing the sprite URL and name.
 */
@Composable
fun PokemonImage(model: PokemonModel) {
    PokemonUtils.getFrontSpriteUrl(model)?.let { spriteUrl ->
        AsyncImage(
            model = spriteUrl,
            contentDescription = "${model.name} sprite",
            modifier = Modifier.size(256.dp)
        )
    }
}