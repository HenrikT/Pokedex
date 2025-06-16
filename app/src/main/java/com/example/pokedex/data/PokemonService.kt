package com.example.pokedex.data

import android.content.Context
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.util.PokemonUtils.MAX_POKEMON_ID
import kotlin.random.Random

/**
 * Provides simplified Pokémon data for the UI layer.
 *
 * Fetches detailed Pokémon models, random entries, and favorite state,
 * combining raw repository data with local user preferences.
 *
 * @param repository Data source for complete Pokémon models and species.
 * @param favoritesManager Local storage manager for user favorites.
 */
class PokemonService(
    private val repository: IPokemonRepository,
    private val favoritesManager: IFavoritesManager
) : IPokemonService {

    override suspend fun getRandomPokemon(): PokemonDetail {
        val id = Random.nextInt(1, MAX_POKEMON_ID + 1)
        return getPokemonDetail(id)
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDetail {
        val (pokemon, entry) = repository.getPokemonWithEntry(id)
            ?: error("Pokémon with ID $id not found")

        val spriteUrl = pokemon.sprites.frontDefault
            ?: error("No image available for Pokémon with ID $id")

        return PokemonDetail(
            id = pokemon.id,
            name = pokemon.name.lowercase(),
            imageUrl = spriteUrl,
            types = pokemon.types,
            entry = entry
        )
    }

    override suspend fun isFavorite(context: Context, id: Int): Boolean {
        return favoritesManager.isFavorite(context, id)
    }

    override suspend fun setFavorite(context: Context, id: Int, isFavorite: Boolean) {
        favoritesManager.setFavorite(context, id, isFavorite)
    }
}