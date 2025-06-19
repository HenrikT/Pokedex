package com.example.pokedex.data

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.example.pokedex.util.ILogger
import com.example.pokedex.util.Logger

/**
 * Repository for retrieving Pokémon data from the PokéAPI.
 *
 * All requests are safe and return null on failure, with errors logged.
 */
object PokemonRepository : IPokemonRepository {

    var logger: ILogger = Logger

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