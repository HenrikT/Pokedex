package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies

/**
 * Interface for retrieving raw Pokémon data from the PokéAPI.
 *
 * Provides methods to retrieve Pokémon models and species metadata,
 * as well as combined data with Pokédex entries. All methods return `null` on failure.
 */
interface IPokemonRepository {

    /**
     * Retrieves the main Pokémon data object for the given ID.
     *
     * @param id The Pokédex ID of the Pokémon to retrieve.
     * @return A [Pokemon] instance, or `null` if the request fails.
     */
    suspend fun getPokemon(id: Int): Pokemon?

    /**
     * Retrieves species metadata for the given Pokémon ID.
     *
     * @param id The species ID (same as Pokédex ID) to retrieve.
     * @return A [PokemonSpecies] instance, or `null` if the request fails.
     */
    suspend fun getSpecies(id: Int): PokemonSpecies?
}