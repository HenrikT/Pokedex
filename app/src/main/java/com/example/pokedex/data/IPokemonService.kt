package com.example.pokedex.data

import android.content.Context
import com.example.pokedex.model.PokemonDetail

/**
 * High-level service interface for accessing and managing Pokémon data.
 *
 * Provides simplified access to Pokémon detail models, random entries,
 * and user-specific favorites.
 */
interface IPokemonService {

    /**
     * Fetches a random Pokémon with complete detail information.
     *
     * Includes name, image, types, and Pokédex entry.
     *
     * @return A fully populated [PokemonDetail] instance.
     */
    suspend fun getRandomPokemon(): PokemonDetail

    /**
     * Fetches full detail information for a specific Pokémon by ID.
     *
     * Includes name, image, types, and Pokédex entry.
     *
     * @param id The national Pokédex ID of the Pokémon.
     * @return A fully populated [PokemonDetail] instance.
     */
    suspend fun getPokemonDetail(id: Int): PokemonDetail

    /**
     * Checks whether the specified Pokémon is marked as a favorite.
     *
     * @param context The context used to access local user preferences.
     * @param id The Pokémon's ID.
     * @return True if the Pokémon is a favorite; false otherwise.
     */
    suspend fun isFavorite(context: Context, id: Int): Boolean

    /**
     * Updates the favorite status of the specified Pokémon.
     *
     * @param context The context used to access local user preferences.
     * @param id The Pokémon's ID.
     * @param isFavorite Whether to mark the Pokémon as a favorite.
     */
    suspend fun setFavorite(context: Context, id: Int, isFavorite: Boolean)
}