package com.trandemsolutions.pokedex.data

import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import com.trandemsolutions.pokedex.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * High-level service for accessing Pokémon data.
 *
 * This class manages caching and retrieval of Pokémon and species details,
 * and provides preloading capabilities for PokémonModel instances used in the UI.
 *
 * Registered as a singleton via Hilt to enable testability and decoupled access.
 */
@Singleton
class PokemonService @Inject constructor(
    private val repository: IPokemonRepository
) : IPokemonService {

    /** In-memory cache of [PokemonModel]s used for UI components and quick access. */
    private val modelCache = mutableListOf<PokemonModel>()

    /** Lock to prevent concurrent writes to the model cache. */
    private val cacheLock = Mutex()

    override fun getAllModels(): List<PokemonModel> = modelCache.sortedBy { it.id }

    override suspend fun getPokemon(id: Int): Pokemon? = repository.getPokemon(id)

    override suspend fun getSpecies(id: Int): PokemonSpecies? = repository.getSpecies(id)

    override suspend fun getModel(id: Int): PokemonModel? {
        val pokemon = repository.getPokemon(id) ?: return null

        return PokemonModel(
            id = pokemon.id,
            name = pokemon.name,
            spriteUrls = pokemon.sprites,
            types = pokemon.types,
        )
    }

    override suspend fun preloadModelsWithProgress(total: Int, onProgress: (Int) -> Unit) {
        cacheLock.withLock {
            modelCache.clear()

            val batchSize = 25
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