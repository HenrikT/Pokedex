package com.example.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.example.pokedex.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * High-level service for accessing Pokémon data.
 *
 * This singleton manages caching and retrieval of Pokémon and species details,
 * and provides preloading capabilities for PokémonModel instances used in the UI.
 */
object PokemonService : IPokemonService {

    /** In-memory cache of [PokemonModel]s used for UI components and quick access. */
    private val modelCache = mutableListOf<PokemonModel>()

    /** Lock to prevent concurrent writes to the model cache. */
    private val cacheLock = Mutex()

    override fun getAllModels(): List<PokemonModel> = modelCache.sortedBy { it.id }

    override suspend fun getPokemon(id: Int): Pokemon? = PokemonRepository.getPokemon(id)

    override suspend fun getSpecies(id: Int): PokemonSpecies? = PokemonRepository.getSpecies(id)

    override suspend fun getModel(id: Int): PokemonModel? {
        val pokemon = PokemonRepository.getPokemon(id) ?: return null
        val species = getSpecies(pokemon.species.id) ?: return null

        return PokemonModel(
            id = pokemon.id,
            name = pokemon.name,
            spriteUrls = pokemon.sprites,
            types = pokemon.types,
            flavorTextEntries = species.flavorTextEntries
        )
    }

    override suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit) {
        cacheLock.withLock {
            modelCache.clear()

            val batchSize = 20
            val ids = (1..total).toList()

            // Load Pokémon in small chunks to reduce memory pressure and avoid API rate limits
            ids.chunked(batchSize).forEach { batch ->
                val results = coroutineScope {
                    batch.map { id ->
                        async(Dispatchers.IO) {
                            val pokemon = getModel(id)
                            pokemon?.let {
                                PokemonModel(
                                    id = it.id,
                                    name = it.name,
                                    types = it.types,
                                    spriteUrls = it.spriteUrls,
                                    flavorTextEntries = it.flavorTextEntries
                                )
                            }
                        }
                    }.awaitAll()
                }

                modelCache.addAll(results.filterNotNull())
                onProgress(modelCache.size)
            }
        }
    }
}