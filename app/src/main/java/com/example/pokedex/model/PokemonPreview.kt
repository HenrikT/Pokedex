package com.example.pokedex.model

/**
 * Lightweight representation of a Pokémon used for overview displays.
 *
 * Contains the minimal fields needed to render a list or detail card.
 *
 * @param id The national Pokédex ID of the Pokémon.
 * @param name The lowercase name of the Pokémon.
 * @param imageUrl The full image URL (default or shiny sprite).
 */
data class PokemonPreview(
    val id: Int,
    val name: String,
    val imageUrl: String
)