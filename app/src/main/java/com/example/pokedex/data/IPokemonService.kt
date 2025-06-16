package com.example.pokedex.data

import android.content.Context
import com.example.pokedex.model.PokemonPreview

/**
 * High-level service interface for accessing and managing Pokémon data.
 */
interface IPokemonService {

    /**
     * Fetches a random Pokémon from the Pokédex.
     */
    suspend fun getRandomPokemon(): PokemonPreview

    /**
     * Fetches a preview for a specific Pokémon by ID.
     *
     * @param id The Pokémon's unique identifier.
     * @return A simplified preview of the Pokémon.
     */
    suspend fun getPreview(id: Int): PokemonPreview

    /**
     * Checks whether a given Pokémon is marked as favorite.
     *
     * @param context The context used to access local storage.
     * @param id The Pokémon ID.
     */
    suspend fun isFavorite(context: Context, id: Int): Boolean

    /**
     * Updates the favorite status of a Pokémon.
     *
     * @param context The context used to access local storage.
     * @param id The Pokémon ID.
     * @param isFavorite Whether the Pokémon should be marked as favorite.
     */
    suspend fun setFavorite(context: Context, id: Int, isFavorite: Boolean)
}