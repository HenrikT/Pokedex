package com.example.pokedex.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "favorites"

/**
 * Provides a datastore instance for storing favorite Pokémon.
 */
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

/**
 * Handles persistence of favorited Pokémon using Jetpack DataStore.
 *
 * Pokémon IDs are stored as a string set under a single preference key.
 * This manager provides access to the saved favorites and utility methods
 * for modifying or querying them.
 */
object FavoritesManager : IFavoritesManager {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_pokemon_ids")

    override suspend fun getFavorites(context: Context): Set<String> {
        return context.dataStore.data.map { it[FAVORITES_KEY] ?: emptySet() }.first()
    }

    override suspend fun isFavorite(context: Context, pokemonId: Int): Boolean {
        val favorites = context.dataStore.data.map { it[FAVORITES_KEY] ?: emptySet() }
        return favorites.map { pokemonId.toString() in it }.first()
    }

    override suspend fun toggleFavorite(context: Context, pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY] ?: emptySet()
            val idStr = pokemonId.toString()
            preferences[FAVORITES_KEY] = if (idStr in current) {
                current - idStr
            } else {
                current + idStr
            }
        }
    }

    override suspend fun setFavorite(context: Context, pokemonId: Int, isFavorite: Boolean) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY] ?: emptySet()
            val idStr = pokemonId.toString()
            preferences[FAVORITES_KEY] = if (isFavorite) {
                current + idStr
            } else {
                current - idStr
            }
        }
    }
}