package com.example.pokedex.data

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

    /**
     * Retrieves the Pokémon object and its English Pokédex entry.
     *
     * Typically used to extract a custom Pokémon model along with its flavor text for UI display.
     *
     * @param id The Pokédex ID of the Pokémon.
     * @return A pair containing the Pokémon and its flavor text entry, or `null` if either fails.
     */
    suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>?
}