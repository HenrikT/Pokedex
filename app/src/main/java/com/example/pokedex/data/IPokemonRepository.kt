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
}