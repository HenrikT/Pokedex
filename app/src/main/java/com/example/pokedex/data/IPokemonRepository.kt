package com.example.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon

/**
 * Repository interface for accessing raw Pokémon data from an external source.
 *
 * Typically backed by a remote API (such as PokéAPI), this repository provides
 * low-level access to full Pokémon objects that contain all stats, images, and metadata.
 */
interface IPokemonRepository {

    /**
     * Fetches a Pokémon by its Pokédex ID.
     *
     * @param id The national Pokédex number of the Pokémon to retrieve.
     * @return A complete Pokémon model including name, ID, sprites, types, and more.
     */
    suspend fun getPokemon(id: Int): Pokemon?

    /**
     * Fetches a Pokémon and its Pokédex flavor text entry.
     *
     * Retrieves both the detailed Pokémon model and a short English entry
     * from its species data. Returns null if either is missing.
     *
     * @param id The national Pokédex number of the Pokémon.
     * @return A pair of [Pokemon] and flavor text, or null if not found.
     */
    suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>?
}