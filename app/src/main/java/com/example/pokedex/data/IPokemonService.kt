package com.example.pokedex.data

import android.content.Context
import com.example.pokedex.model.PokemonDetail

/**
 * High-level service interface for accessing and managing Pokémon data.
 *
 * Provides simplified access to Pokémon detail models, random entries,
 * and user-specific "My Pokémon" state.
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
     * Checks whether the specified Pokémon is in "My Pokémon".
     *
     * @param context The context used to access local user preferences.
     * @param id The Pokémon's ID.
     * @return True if the Pokémon is saved in "My Pokémon"; false otherwise.
     */
    suspend fun isInMyPokemon(context: Context, id: Int): Boolean

    /**
     * Updates the presence of the specified Pokémon in "My Pokémon".
     *
     * @param context The context used to access local user preferences.
     * @param id The Pokémon's ID.
     * @param save Whether to save the Pokémon in "My Pokémon".
     */
    suspend fun setMyPokemon(context: Context, id: Int, save: Boolean)
}