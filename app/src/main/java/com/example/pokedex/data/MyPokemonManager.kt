package com.example.pokedex.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "my_pokemon"

/**
 * Provides a data store instance for storing Pokémon-related preferences.
 */
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

/**
 * Handles persistence of caught Pokémon using Jetpack DataStore.
 *
 * Pokémon IDs are stored as a string set under a single preference key.
 * This manager provides access to the caught Pokémon set as a [Flow],
 * and utility methods for modifying or querying it.
 */
object MyPokemonManager : IMyPokemonManager {

    private val CAUGHT_POKEMON_KEY = stringSetPreferencesKey("my_pokemon_ids")

    override fun getCaughtPokemon(context: Context): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
        }
    }

    override suspend fun isCaught(context: Context, pokemonId: Int): Boolean {
        val caught = context.dataStore.data.map { it[CAUGHT_POKEMON_KEY] ?: emptySet() }
        return caught.map { pokemonId.toString() in it }.first()
    }

    override suspend fun catchPokemon(context: Context, pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            val idStr = pokemonId.toString()
            preferences[CAUGHT_POKEMON_KEY] = current + idStr
        }
    }

    override suspend fun releasePokemon(context: Context, pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            val idStr = pokemonId.toString()
            preferences[CAUGHT_POKEMON_KEY] = current - idStr
        }
    }

    override suspend fun toggleCaught(context: Context, pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            val idStr = pokemonId.toString()
            preferences[CAUGHT_POKEMON_KEY] = if (idStr in current) {
                current - idStr
            } else {
                current + idStr
            }
        }
    }
}