package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.trandemsolutions.pokedex.util.ILogger
import javax.inject.Inject

/**
 * Repository for retrieving Pokémon data from the PokéAPI.
 *
 * This implementation wraps the network calls and provides
 * null-safe access with error logging.
 *
 * It is injected using Hilt and used by higher-level services like [IPokemonService].
 *
 * @param logger A logger abstraction for error tracking, useful for testing and flexibility.
 */
class PokemonRepository @Inject constructor(
    private val logger: ILogger
) : IPokemonRepository {

    override suspend fun getPokemon(id: Int): Pokemon? {
        return try {
            PokeApi.getPokemon(id).getOrNull()
        } catch (e: Exception) {
            logger.e("PokemonRepository", "Failed to get Pokémon $id", e)
            null
        }
    }

    override suspend fun getSpecies(id: Int): PokemonSpecies? {
        return try {
            PokeApi.getPokemonSpecies(id).getOrNull()
        } catch (e: Exception) {
            logger.e("PokemonRepository", "Failed to get species $id", e)
            null
        }
    }
}