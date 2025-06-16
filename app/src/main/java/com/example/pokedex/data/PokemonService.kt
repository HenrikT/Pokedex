package com.example.pokedex.data

import com.example.pokedex.model.PokemonPreview
import kotlin.random.Random

/**
 * Implementation of [IPokemonService] that uses [IPokemonRepository] to fetch Pokémon data.
 *
 * This service provides high-level access to simplified Pokémon models used in the UI,
 * including random selection and data formatting.
 *
 * @param repository The repository used to retrieve full Pokémon details from the API.
 */
class PokemonService(
    private val repository: IPokemonRepository
) : IPokemonService {

    override suspend fun getRandomPokemon(): PokemonPreview {
        val id = Random.nextInt(1, MAX_POKEMON_ID + 1)
        val pokemon = repository.getPokemon(id) ?: error("Pokémon with ID $id not found")

        val spriteUrl = pokemon.sprites.frontDefault
            ?: error("No image available for Pokémon with ID $id")

        return PokemonPreview(
            id = pokemon.id,
            name = pokemon.name.lowercase(),
            imageUrl = spriteUrl
        )
    }

    companion object {
        private const val MAX_POKEMON_ID = 1025
    }
}