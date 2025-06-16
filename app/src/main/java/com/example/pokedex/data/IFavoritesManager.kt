package com.example.pokedex.data

import android.content.Context

/**
 * Abstraction for managing a user's favorite Pokémon.
 *
 * Provides utility methods for checking, toggling, and accessing
 * favorite status of Pokémon based on their numeric IDs.
 */
interface IFavoritesManager {

    /**
     * Returns the full set of favorited Pokémon IDs as strings.
     *
     * @param context The application context used to access the DataStore.
     * @return A set of Pokémon IDs currently marked as favorite.
     */
    suspend fun getFavorites(context: Context): Set<String>

    /**
     * Checks whether the given Pokémon ID is currently marked as favorite.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to check.
     * @return `true` if the Pokémon is a favorite, `false` otherwise.
     */
    suspend fun isFavorite(context: Context, pokemonId: Int): Boolean

    /**
     * Toggles the favorite state of the given Pokémon ID.
     *
     * If the Pokémon was a favorite, it is removed. Otherwise, it is added.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to toggle.
     */
    suspend fun toggleFavorite(context: Context, pokemonId: Int)

    /**
     * Explicitly sets whether the Pokémon should be a favorite.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to update.
     * @param isFavorite Whether the Pokémon should be marked as favorite.
     */
    suspend fun setFavorite(context: Context, pokemonId: Int, isFavorite: Boolean)
}