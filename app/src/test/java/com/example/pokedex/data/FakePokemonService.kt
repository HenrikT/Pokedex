package com.example.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.example.pokedex.model.PokemonModel

/**
 * Fake implementation of [IPokemonService] for testing.
 *
 * Bypasses real API/cache logic and returns fixed data.
 */
class FakePokemonService(
    private val fakeRepo: IPokemonRepository,
) : IPokemonService {

    private val summaries = mutableListOf<PokemonModel>()

    override fun getAllModels(): List<PokemonModel> = summaries

    override suspend fun getPokemon(id: Int): Pokemon? = fakeRepo.getPokemon(id)

    override suspend fun getSpecies(id: Int): PokemonSpecies? = fakeRepo.getSpecies(id)

    override suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>? =
        fakeRepo.getPokemonWithEntry(id)

    override suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit) {
        summaries.clear()
        for (id in 1..total) {
            val poke = fakeRepo.getPokemon(id)
            poke?.let {
                summaries.add(
                    PokemonModel(
                        id = it.id,
                        name = it.name,
                        spriteUrl = it.sprites.frontDefault ?: "",
                        types = it.types
                    )
                )
                onProgress(summaries.size)
            }
        }
    }

    override suspend fun getModel(id: Int): PokemonModel? {
        val poke = fakeRepo.getPokemon(id) ?: return null
        return PokemonModel(
            id = poke.id,
            name = poke.name,
            spriteUrl = poke.sprites.frontDefault ?: return null,
            types = poke.types
        )
    }
}