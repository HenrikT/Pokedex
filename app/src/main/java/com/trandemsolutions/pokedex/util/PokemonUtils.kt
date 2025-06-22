package com.trandemsolutions.pokedex.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import co.pokeapi.pokekotlin.model.PokemonSpeciesFlavorText
import com.trandemsolutions.pokedex.model.PokemonModel
import kotlin.random.Random

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
     * Returns the first English flavor text from a list of entries.
     *
     * Used to extract a readable Pokédex description for display purposes.
     *
     * @param entries A list of flavor text entries from a Pokémon species.
     * @return The first English flavor text, cleaned of formatting characters, or a fallback string if not found.
     */
    fun getEnglishFlavorText(entries: List<PokemonSpeciesFlavorText>): String {
        return entries
            .firstOrNull { it.language.name == "en" }
            ?.flavorText
            ?.replace("\n", " ")
            ?.replace("\u000c", " ")
            ?: "No entry found"
    }

    /**
     * Returns the front-facing sprite URL for a given Pokémon model.
     *
     * This method safely extracts the default front sprite from a [PokemonModel].
     *
     * @param model The Pokémon to extract the image URL from.
     * @return A URL string to the front sprite, or `null` if not available.
     */
    fun getFrontSpriteUrl(model: PokemonModel): String? {
        return model.spriteUrls.frontDefault
    }

    /**
     * Returns the shiny front-facing sprite URL for a given Pokémon model.
     *
     * This method safely extracts the shiny front sprite from a [PokemonModel].
     *
     * @param model The Pokémon to extract the shiny image URL from.
     * @return A URL string to the shiny front sprite, or `null` if not available.
     */
    fun getShinyFrontSpriteUrl(model: PokemonModel): String? {
        return model.spriteUrls.frontShiny
    }

    /**
     * Returns the PokémonDetail's name with the first letter capitalized.
     */
    fun getName(pokemon: PokemonModel): String {
        return pokemon.name.replaceFirstChar { it.uppercase() }
    }

    /**
     * Returns the PokémonDetail's ID as a string.
     */
    fun getId(pokemon: PokemonModel): String {
        return "#${pokemon.id}"
    }

    /**
     * Formats the Pokémon's name and ID, optionally with a shiny star prefix.
     *
     * If the Pokémon is shiny, a "★" is added before the name.
     * Example output: "★ Swellow #277" or "Swellow #277"
     *
     * @param pokemon The [PokemonModel] to format.
     * @param isShiny Whether the shiny star should be prefixed.
     * @return A formatted string with optional shiny indicator.
     */
    fun getFormattedPokemonName(pokemon: PokemonModel, isShiny: Boolean = false): String {
        val star = if (isShiny) "★ " else ""
        return "$star${getName(pokemon)} ${getId(pokemon)}"
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

    /**
     * Returns a gradient background brush based on the Pokémon's types.
     *
     * If two types are given, a diagonal gradient is used. If only one is available,
     * a vertical gradient is created from that color.
     *
     * @param types A list of Pokémon type names.
     * @return A [Brush] that represents the type-based background.
     */
    fun getTypeBackground(types: List<String>): Brush {
        return when (types.size) {
            2 -> Brush.linearGradient(
                colors = listOf(getTypeColor(types[0]), getTypeColor(types[1])),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
            )

            else -> Brush.verticalGradient(
                colors = listOf(
                    getTypeColor(types.firstOrNull() ?: ""),
                    getTypeColor(types.firstOrNull() ?: "")
                )
            )
        }
    }

    /**
     * Returns true with a 1 in 10 chance to simulate shiny Pokémon encounters.
     *
     * Can be used whenever a random Pokémon is shown to determine if it is shiny.
     *
     * @return `true` if the encounter should be shiny, `false` otherwise.
     */
    fun isShinyEncounter(): Boolean {
        return Random.nextInt(10) == 0
    }

    /**
     * Used to support fuzzy search in the pokédex, making it easier for kids to find their pokémon.
     *
     * Returns a similarity score between two strings using Levenshtein distance.
     *
     * The score is normalized between 0.0 (completely different) and 1.0 (identical).
     *
     * @param s1 First string.
     * @param s2 Second string.
     * @return Similarity score as a [Double] from 0.0 to 1.0.
     */
    fun similarity(s1: String, s2: String): Double {
        val l1 = s1.lowercase()
        val l2 = s2.lowercase()
        val distance = levenshtein(l1, l2)
        val maxLen = maxOf(l1.length, l2.length).coerceAtLeast(1)
        return 1.0 - distance.toDouble() / maxLen
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     *
     * This algorithm returns the minimum number of single-character edits
     * (insertions, deletions, or substitutions) required to change one string into the other.
     *
     * @param lhs The first string.
     * @param rhs The second string.
     * @return The Levenshtein distance as an [Int].
     */
    private fun levenshtein(lhs: String, rhs: String): Int {
        val lhsLen = lhs.length
        val rhsLen = rhs.length

        val cost = Array(lhsLen + 1) { IntArray(rhsLen + 1) }

        for (i in 0..lhsLen) cost[i][0] = i
        for (j in 0..rhsLen) cost[0][j] = j

        for (i in 1..lhsLen) {
            for (j in 1..rhsLen) {
                val substitutionCost = if (lhs[i - 1] == rhs[j - 1]) 0 else 1
                cost[i][j] = minOf(
                    cost[i - 1][j] + 1,
                    cost[i][j - 1] + 1,
                    cost[i - 1][j - 1] + substitutionCost
                )
            }
        }

        return cost[lhsLen][rhsLen]
    }
}