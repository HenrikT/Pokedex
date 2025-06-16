package com.example.pokedex.data

import com.example.pokedex.model.PokemonDetail
import kotlin.random.Random

/**
 * Provides simplified Pokémon data for the UI layer.
 *
 * Fetches preview models, random entries, and favorite state,
 * combining raw repository data with local user preferences.
 *
 * @param repository Data source for complete Pokémon models.
 * @param favoritesManager Local storage manager for user favorites.
 */
class PokemonService(
    private val repository: IPokemonRepository,
    private val favoritesManager: IFavoritesManager
) : IPokemonService {

    override suspend fun getRandomPokemon(): PokemonDetail {
        val id = Random.nextInt(1, MAX_POKEMON_ID + 1)
        return getPreview(id)
    }

    override suspend fun getPreview(id: Int): PokemonDetail {
        val pokemon = repository.getPokemon(id) ?: error("Pokémon with ID $id not found")

        val spriteUrl = pokemon.sprites.frontDefault
            ?: error("No image available for Pokémon with ID $id")

        val types = pokemon.types.map { it.type.name }

        return PokemonDetail(
            id = pokemon.id,
            name = pokemon.name.lowercase(),
            imageUrl = spriteUrl,
            types = types
        )
    }

    override suspend fun isFavorite(context: android.content.Context, id: Int): Boolean {
        return favoritesManager.isFavorite(context, id)
    }

    override suspend fun setFavorite(context: android.content.Context, id: Int, isFavorite: Boolean) {
        favoritesManager.setFavorite(context, id, isFavorite)
    }

    companion object {
        private const val MAX_POKEMON_ID = 1025
    }
}