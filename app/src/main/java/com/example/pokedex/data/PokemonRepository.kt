package com.example.pokedex.data

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.example.pokedex.util.ILogger
import com.example.pokedex.util.Logger

/**
 * Implementation of [IPokemonRepository] that retrieves Pokémon data using the PokéKotlin client.
 *
 * Connects to the PokéAPI and returns basic Pokémon information by ID.
 */
class PokemonRepository : IPokemonRepository {

    /**
     * Logger used for reporting data-fetch errors.
     * Can be overridden for test environments.
     */
    var logger: ILogger = Logger

    /**
     * Retrieves the main Pokémon data object for the given ID.
     *
     * @param id The Pokédex ID of the Pokémon to retrieve.
     * @return A [Pokemon] instance, or `null` if the request fails.
     */
    override suspend fun getPokemon(id: Int): Pokemon? {
        return try {
            PokeApi.getPokemon(id).getOrNull()
        } catch (e: Exception) {
            logger.e("PokemonRepository", "Failed to get Pokémon $id", e)
            null
        }
    }

    /**
     * Retrieves both the Pokémon and its Pokédex flavor text.
     *
     * Uses [getPokemon] and [getSpecies] to extract the English description.
     * If either request fails, returns null.
     *
     * @param id The Pokédex ID of the Pokémon to retrieve.
     * @return A pair of [Pokemon] and English flavor text, or `null` if not found.
     */
    override suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>? {
        val pokemon = getPokemon(id) ?: return null
        val species = getSpecies(pokemon.species.id) ?: return null

        val entry = species.flavorTextEntries
            .firstOrNull { it.language.name == "en" }
            ?.flavorText
            ?.replace("\n", " ")
            ?.replace("\u000c", " ")
            ?: "No entry found"

        return pokemon to entry
    }

    /**
     * Retrieves species-level metadata for a given Pokémon.
     *
     * Used to access additional details like Pokédex entries, growth rate,
     * egg groups, and localized flavor text. This is required because the
     * core Pokémon object does not include these fields.
     *
     * @param id The species ID associated with a Pokémon.
     * @return A [PokemonSpecies] object, or `null` if the request fails.
     */
    private suspend fun getSpecies(id: Int): PokemonSpecies? {
        return try {
            PokeApi.getPokemonSpecies(id).getOrNull()
        } catch (e: Exception) {
            logger.e("PokemonRepository", "Failed to get species $id", e)
            null
        }
    }
}