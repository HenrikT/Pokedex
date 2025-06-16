package com.example.pokedex.model

import co.pokeapi.pokekotlin.model.PokemonType

/**
 * Detailed representation of a Pokémon used for the main card view.
 *
 * Includes all data required to show an image, name, type info, and Pokédex flavor text.
 *
 * @param id The national Pokédex ID of the Pokémon.
 * @param name The lowercase name of the Pokémon.
 * @param imageUrl The sprite image URL used for display.
 * @param types All types this Pokémon has, in order of slot.
 * @param entry The English Pokédex flavor text for this Pokémon.
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val entry: String
)