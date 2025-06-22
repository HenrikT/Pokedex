package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.trandemsolutions.pokedex.model.PokemonModel

/**
 * Interface for accessing Pokémon data.
 *
 * This abstraction allows multiple implementations and is injected using Hilt.
 * It provides core methods for retrieving full Pokémon data, species metadata,
 * and lightweight models used in the UI. Also supports background preloading
 * with progress tracking for performance-critical flows.
 */
interface IPokemonService {

    /**
     * Returns a list of all available Pokémon models.
     *
     * @return Sorted list of [PokemonModel] objects.
     */
    fun getAllModels(): List<PokemonModel>

    /**
     * Returns detailed [Pokemon] data for the given ID.
     *
     * @param id The Pokédex ID.
     * @return The [Pokemon] object or `null` if not found.
     */
    suspend fun getPokemon(id: Int): Pokemon?

    /**
     * Returns [PokemonSpecies] data for the given ID.
     *
     * @param id The species ID.
     * @return The [PokemonSpecies] object or `null` if not found.
     */
    suspend fun getSpecies(id: Int): PokemonSpecies?

    /**
     * Returns a full Pokémon model for a given ID.
     *
     * Loads both the basic Pokémon data and species metadata, including all
     * Pokédex flavor text entries. Used for displaying detailed Pokémon information
     * in the UI without fetching data multiple times.
     *
     * @param id The national Pokédex ID of the Pokémon.
     * @return A [PokemonModel] containing name, sprite and types, or `null` if not found.
     */
    suspend fun getModel(id: Int): PokemonModel?

    /**
     * Preloads Pokémon models and reports progress.
     *
     * @param total Number of Pokémon to load.
     * @param onProgress Callback invoked with current progress count.
     */
    suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit)

}