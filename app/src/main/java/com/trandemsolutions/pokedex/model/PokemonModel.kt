package com.trandemsolutions.pokedex.model

import co.pokeapi.pokekotlin.model.PokemonSprites
import co.pokeapi.pokekotlin.model.PokemonType

/**
 * Our custom model with basic Pokémon data for UI use.
 *
 * @param id The national Pokédex ID of the Pokémon.
 * @param name The lowercase name of the Pokémon.
 * @param spriteUrls The sprite image URLs used for display.
 * @param types The types of the Pokémon. Either one or two.
 */
data class PokemonModel(
    val id: Int,
    val name: String,
    val spriteUrls: PokemonSprites,
    val types: List<PokemonType>
)