package com.example.pokedex.model

/**
 * Represents a simplified Pokémon used in UI cards or detail views.
 *
 * Includes only essential data such as ID, name, image, and type list
 * to render a compact preview without relying on full API models.
 *
 * @param id The national Pokédex ID of the Pokémon.
 * @param name The lowercase name of the Pokémon.
 * @param imageUrl URL to the front-facing sprite image.
 * @param types A list of lowercase Pokémon type names (e.g. "fire", "grass").
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>
)