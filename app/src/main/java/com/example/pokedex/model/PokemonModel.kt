package com.example.pokedex.model

import co.pokeapi.pokekotlin.model.PokemonType

/**
 * Our custom model with basic Pokémon data for UI use.
 *
 * @param id The national Pokédex ID of the Pokémon.
 * @param name The lowercase name of the Pokémon.
 * @param spriteUrl The sprite image URL used for display.
 * @param types The types of the Pokémon. Either one or two.
 */
data class PokemonModel(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val types: List<PokemonType>,
)