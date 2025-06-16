package com.example.pokedex.data

import com.example.pokedex.model.PokemonPreview

/**
 * High-level service interface for accessing and managing Pokémon data.
 *
 * Used by the UI layer to retrieve simplified Pokémon models such as previews or detailed info.
 * This service abstracts away repository logic and external API concerns.
 */
interface IPokemonService {

    /**
     * Fetches a random Pokémon from the Pokédex.
     *
     * This method is used to retrieve a new Pokémon every time the user opens the app
     * or explicitly requests one. The Pokémon will include only the necessary preview data
     * used in overview displays.
     *
     * @return A preview of a random Pokémon, including ID, name, and image URL.
     */
    suspend fun getRandomPokemon(): PokemonPreview
}