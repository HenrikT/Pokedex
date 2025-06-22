package com.trandemsolutions.pokedex.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for managing a user's caught Pokémon collection.
 *
 * Provides utility methods for checking, updating, and observing caught Pokémon,
 * including optional support for shiny status.
 */
interface IMyPokemonManager {

    /**
     * Returns a [Flow] of the current set of caught Pokémon IDs.
     *
     * @param context The application context used to access the DataStore.
     * @return A [Flow] emitting a [Set] of Pokémon IDs as strings.
     */
    fun getCaughtPokemon(context: Context): Flow<Set<String>>

    /**
     * Checks whether the given Pokémon ID is currently caught.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to check.
     * @return `true` if the Pokémon is caught, `false` otherwise.
     */
    suspend fun isCaught(context: Context, pokemonId: Int): Boolean

    /**
     * Checks whether the given Pokémon is caught as shiny.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID.
     * @return `true` if the shiny variant is caught, `false` otherwise.
     */
    suspend fun isCaughtShiny(context: Context, pokemonId: Int): Boolean

    /**
     * Catches the given Pokémon, optionally as shiny.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to add.
     * @param isShiny Whether the caught variant is shiny.
     */
    suspend fun catchPokemon(context: Context, pokemonId: Int, isShiny: Boolean = false)

    /**
     * Releases the given Pokémon.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to remove.
     */
    suspend fun releasePokemon(context: Context, pokemonId: Int)

    /**
     * Toggles the caught status of a Pokémon ID.
     *
     * If the ID is already in the set, it is removed. If not, it is added.
     *
     * @param context The application context used to access the DataStore.
     * @param pokemonId The Pokémon's numeric ID to toggle.
     */
    suspend fun toggleCaught(context: Context, pokemonId: Int, isShiny: Boolean)
}
