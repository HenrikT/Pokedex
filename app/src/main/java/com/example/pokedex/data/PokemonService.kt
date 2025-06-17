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

    /** In-memory cache of detailed Pokémon data keyed by Pokédex ID. */
    private val pokemonCache = mutableMapOf<Int, Pokemon>()

    /** In-memory cache of Pokémon species data keyed by species ID. */
    private val speciesCache = mutableMapOf<Int, PokemonSpecies>()

    /** In-memory cache of [PokemonModel]s used for UI components and quick access. */
    private val modelCache = mutableListOf<PokemonModel>()

    /** Lock to prevent concurrent writes to the model cache. */
    private val cacheLock = Mutex()

    override fun getAllModels(): List<PokemonModel> = modelCache.sortedBy { it.id }

    override suspend fun getPokemon(id: Int): Pokemon? {
        return pokemonCache[id] ?: PokemonRepository.getPokemon(id)?.also {
            pokemonCache[id] = it
        }
    }

    override suspend fun getSpecies(id: Int): PokemonSpecies? {
        return speciesCache[id] ?: PokemonRepository.getSpecies(id)?.also {
            speciesCache[id] = it
        }
    }

    override suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>? {
        val pokemon = getPokemon(id) ?: return null
        val species = getSpecies(pokemon.species.id) ?: return null

        val entry = species.flavorTextEntries
            .firstOrNull { it.language.name == "en" }
            ?.flavorText
            ?.replace("\n", " ")
            ?.replace("\u000c", " ")
            ?: "No entry found"

        return pokemon to entry
    }

    override suspend fun getModel(id: Int): PokemonModel? {
        val pokemon = PokemonRepository.getPokemon(id) ?: return null

        return PokemonModel(
            id = pokemon.id,
            name = pokemon.name,
            spriteUrl = pokemon.sprites.frontDefault ?: return null,
            types = pokemon.types
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
                            val pokemon = PokemonRepository.getPokemon(id)
                            pokemon?.let {
                                PokemonModel(
                                    id = it.id,
                                    name = it.name,
                                    types = it.types,
                                    spriteUrl = it.sprites.frontDefault.orEmpty()
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