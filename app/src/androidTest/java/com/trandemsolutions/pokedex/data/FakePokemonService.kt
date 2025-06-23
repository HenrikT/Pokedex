package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.model.NamedApiResource
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import co.pokeapi.pokekotlin.model.PokemonSprites
import co.pokeapi.pokekotlin.model.PokemonType
import com.trandemsolutions.pokedex.model.PokemonModel
import jakarta.inject.Inject

/**
 * Fake implementation of [IPokemonService] for testing.
 *
 * Bypasses real API/cache logic and returns fixed data.
 */
class FakePokemonService @Inject constructor(
    private val fakeRepo: IPokemonRepository
) : IPokemonService {

    private val summaries = mutableListOf<PokemonModel>()

    init {
        summaries.add(
            PokemonModel(
                id = 1,
                name = "bulbasaur",
                spriteUrls = createFakeSprites(),
                types = listOf(createFakeType())
            )
        )
    }

    override fun getAllModels(): List<PokemonModel> = summaries

    override suspend fun getPokemon(id: Int): Pokemon? = fakeRepo.getPokemon(id)

    override suspend fun getSpecies(id: Int): PokemonSpecies? = fakeRepo.getSpecies(id)

    override suspend fun getModel(id: Int): PokemonModel? {
        return summaries.firstOrNull { it.id == id }
    }

    override suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit) {
        // Already preloaded one item in init
        onProgress(summaries.size)
    }

    private fun createFakeSprites(): PokemonSprites {
        return PokemonSprites(
            frontDefault = "https://img.com/bulba.png",
            frontShiny = null,
            backDefault = null,
            backShiny = null,
            frontFemale = null,
            backFemale = null,
            frontShinyFemale = null,
            backShinyFemale = null
        )
    }

    private fun createFakeType(): PokemonType {
        return PokemonType(
            slot = 1,
            type = NamedApiResource("grass", "1")
        )
    }
}