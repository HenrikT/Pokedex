package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.trandemsolutions.pokedex.model.PokemonModel

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

    override suspend fun getModel(id: Int): PokemonModel? {
        val pokemon = fakeRepo.getPokemon(id) ?: return null

        return PokemonModel(
            id = pokemon.id,
            name = pokemon.name,
            spriteUrls = pokemon.sprites,
            types = pokemon.types
        )
    }

    override suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit) {
        summaries.clear()
        for (id in 1..total) {
            val model = getModel(id)
            if (model != null) {
                summaries.add(model)
                onProgress(summaries.size)
            }
        }
    }
}