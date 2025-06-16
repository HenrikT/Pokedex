package com.example.pokedex.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex.model.PokemonDetail

/**
 * Utility class for Pokémon-related operations and constants.
 *
 * This includes color mappings for types and other visual helpers
 * that make the app more engaging and themed.
 */
object PokemonUtils {

    /** The highest known National Dex ID for a Pokémon (Gen 9). */
    const val MAX_POKEMON_ID = 1025

    /** Background color used in Pokémon card containers. */
    val PokemonCardContainerBackground = Color(0x7A5E5E5E)

    /**
     * Returns the Pokémon's name with the first letter capitalized.
     */
    fun getName(pokemon: PokemonDetail): String {
        return pokemon.name.replaceFirstChar { it.uppercase() }
    }

    /**
     * Returns the Pokémon's ID as a string.
     */
    fun getId(pokemon: PokemonDetail): String {
        return "#${pokemon.id}"
    }

    /**
     * Formats the Pokémon's name with its ID in the form: "Swellow #277".
     */
    fun getFormattedPokemonName(pokemon: PokemonDetail): String {
        return "${getName(pokemon)} ${getId(pokemon)}"
    }

    /**
     * Maps Pokémon types to their official color codes.
     *
     * These colors are used to make the UI more appealing and
     * kid-friendly by reflecting type-based identities.
     */
    val typeColors: Map<String, Color> = mapOf(
        "normal" to Color(0xFFA8A77A),
        "fire" to Color(0xFFEE8130),
        "water" to Color(0xFF6390F0),
        "electric" to Color(0xFFF7D02C),
        "grass" to Color(0xFF7AC74C),
        "ice" to Color(0xFF96D9D6),
        "fighting" to Color(0xFFC22E28),
        "poison" to Color(0xFFA33EA1),
        "ground" to Color(0xFFE2BF65),
        "flying" to Color(0xFFA98FF3),
        "psychic" to Color(0xFFF95587),
        "bug" to Color(0xFFA6B91A),
        "rock" to Color(0xFFB6A136),
        "ghost" to Color(0xFF735797),
        "dragon" to Color(0xFF6F35FC),
        "dark" to Color(0xFF705746),
        "steel" to Color(0xFFB7B7CE),
        "fairy" to Color(0xFFD685AD)
    )

    /**
     * Returns the color associated with the given Pokémon type.
     *
     * Defaults to [Color.Gray] if the type is unknown.
     *
     * @param type The lowercase Pokémon type (e.g. "fire").
     * @return The corresponding [Color].
     */
    fun getTypeColor(type: String): Color {
        return typeColors[type.lowercase()] ?: Color.Gray
    }
}