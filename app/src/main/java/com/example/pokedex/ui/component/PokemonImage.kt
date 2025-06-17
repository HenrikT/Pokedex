package com.example.pokedex.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.pokeapi.pokekotlin.model.Pokemon
import coil.compose.AsyncImage

/**
 * Displays the Pokémon's front-facing sprite image.
 *
 * Uses Coil's [AsyncImage] to load the sprite from the provided URL.
 * If the sprite URL is null, no image is shown.
 *
 * @param pokemon The [Pokemon] object containing sprite URL and name metadata.
 */
@Composable
fun PokemonImage(pokemon: Pokemon) {
    pokemon.sprites.frontDefault?.let { spriteUrl ->
        AsyncImage(
            model = spriteUrl,
            contentDescription = "${pokemon.name} sprite",
            modifier = Modifier.size(256.dp)
        )
    }
}