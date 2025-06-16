package com.example.pokedex.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for managing a user's personal collection of Pokémon ("My Pokémon").
 *
 * Provides utility methods for checking, toggling, and observing the Pokémon a user has saved.
 */
interface IMyPokemonManager {

    /**
     * Returns a [Flow] of the current set of saved Pokémon IDs.
     *
     * @param context The application context used to access the DataStore.
     * @return A [Flow] emitting a [Set] of Pokémon IDs as strings.
     */
    fun getMyPokemon(context: Context): Flow<Set<String>>

    /**
     * Checks whether the given Pokémon ID is currently in "My Pokémon".
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to check.
     * @return `true` if the Pokémon is saved, `false` otherwise.
     */
    suspend fun isInMyPokemon(context: Context, pokemonId: Int): Boolean

    /**
     * Toggles the presence of a Pokémon ID in the saved list.
     *
     * If the ID is already in the set, it is removed. If not, it is added.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to toggle.
     */
    suspend fun toggleMyPokemon(context: Context, pokemonId: Int)

    /**
     * Explicitly sets whether the Pokémon should be in "My Pokémon".
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to update.
     * @param isSaved Whether the Pokémon should be included in the set.
     */
    suspend fun setMyPokemon(context: Context, pokemonId: Int, isSaved: Boolean)
}