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
 * Shiny Pokémon are stored with an "s:" prefix.
 */
object MyPokemonManager : IMyPokemonManager {

    private val CAUGHT_POKEMON_KEY = stringSetPreferencesKey("my_pokemon_ids")

    override fun getCaughtPokemon(context: Context): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
        }
    }

    override suspend fun isCaught(context: Context, pokemonId: Int): Boolean {
        val allIds = context.dataStore.data.map { it[CAUGHT_POKEMON_KEY] ?: emptySet() }
        return allIds.map {
            it.any { raw -> parseCaughtId(raw)?.id == pokemonId }
        }.first()
    }

    override suspend fun isCaughtShiny(context: Context, pokemonId: Int): Boolean {
        val caught = context.dataStore.data.map { it[CAUGHT_POKEMON_KEY] ?: emptySet() }
        return caught.map { "s:${pokemonId}" in it }.first()
    }

    override suspend fun catchPokemon(context: Context, pokemonId: Int, isShiny: Boolean) {
        val prefix = if (isShiny) "s:" else "n:"
        val idStr = "$prefix$pokemonId"
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            preferences[CAUGHT_POKEMON_KEY] = current + idStr
        }
    }

    override suspend fun releasePokemon(context: Context, pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            preferences[CAUGHT_POKEMON_KEY] = current - pokemonId.toString() - "s:$pokemonId"
        }
    }

    override suspend fun toggleCaught(context: Context, pokemonId: Int, isShiny: Boolean) {
        context.dataStore.edit { preferences ->
            val current = preferences[CAUGHT_POKEMON_KEY] ?: emptySet()
            val normalId = pokemonId.toString()
            val shinyId = "s:$pokemonId"
            val targetId = if (isShiny) shinyId else normalId

            preferences[CAUGHT_POKEMON_KEY] = if (targetId in current) {
                current - targetId
            } else {
                // Remove other variant if it exists before adding this one
                val withoutOtherVariant = current - normalId - shinyId
                withoutOtherVariant + targetId
            }
        }
    }
}

data class CaughtPokemonId(val id: Int, val isShiny: Boolean)

fun parseCaughtId(raw: String): CaughtPokemonId? {
    val shiny = raw.startsWith("s:")
    val normal = raw.startsWith("n:")

    val numericPart = when {
        shiny || normal -> raw.substring(2)
        else -> raw // fallback for old entries without prefix
    }

    return numericPart.toIntOrNull()?.let { CaughtPokemonId(it, shiny) }
}