package com.example.pokedex.data

import co.pokeapi.pokekotlin.model.ApiResource
import co.pokeapi.pokekotlin.model.NamedApiResource
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSpecies
import co.pokeapi.pokekotlin.model.PokemonSpeciesFlavorText
import co.pokeapi.pokekotlin.model.PokemonSprites
import co.pokeapi.pokekotlin.model.PokemonType as Type

/**
 * Fake implementation of [IPokemonRepository] for testing purposes.
 *
 * Returns a preconfigured Pokémon object and entry string to simulate repository behavior without external dependencies.
 */
class FakePokemonRepository : IPokemonRepository {

    override suspend fun getPokemon(id: Int): Pokemon? {
        return createFakePokemon(id)
    }

    override suspend fun getSpecies(id: Int): PokemonSpecies? {
        return PokemonSpecies(
            id = id,
            name = "bulbasaur",
            baseHappiness = 70,
            captureRate = 45,
            color = NamedApiResource("green", "1"),
            eggGroups = emptyList(),
            evolvesFromSpecies = null,
            evolutionChain = ApiResource("1"),
            flavorTextEntries = listOf(
                PokemonSpeciesFlavorText(
                    flavorText = "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon.",
                    language = NamedApiResource("en", "9"),
                    version = NamedApiResource("red", "1")
                )
            ),
            formDescriptions = emptyList(),
            formsSwitchable = true,
            genderRate = 1,
            genera = emptyList(),
            generation = NamedApiResource("generation-i", "1"),
            growthRate = NamedApiResource("medium-slow", "1"),
            habitat = NamedApiResource("grassland", "3"),
            hasGenderDifferences = false,
            hatchCounter = 20,
            isBaby = false,
            isLegendary = false,
            isMythical = false,
            names = emptyList(),
            order = 1,
            palParkEncounters = emptyList(),
            pokedexNumbers = emptyList(),
            shape = NamedApiResource("quadruped", "6"),
            varieties = emptyList()
        )
    }

    override suspend fun getPokemonWithEntry(id: Int): Pair<Pokemon, String>? {
        return createFakePokemon(id) to "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon."
    }

    private fun createFakePokemon(id: Int): Pokemon {
        return Pokemon(
            id = id,
            name = "bulbasaur",
            baseExperience = 64,
            height = 7,
            isDefault = true,
            order = 1,
            weight = 69,
            species = NamedApiResource(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon-species/1/"),
            abilities = emptyList(),
            forms = emptyList(),
            gameIndices = emptyList(),
            heldItems = emptyList(),
            moves = emptyList(),
            stats = emptyList(),
            types = listOf(Type(slot = 1, type = NamedApiResource("grass", "1"))),
            sprites = PokemonSprites(
                backDefault = null,
                backShiny = null,
                frontDefault = "https://img.com/bulba.png",
                frontShiny = null,
                backFemale = null,
                backShinyFemale = null,
                frontFemale = null,
                frontShinyFemale = null
            )
        )
    }
}