package com.example.pokedex.data

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokemon
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
}