package com.example.pokedex.data

import co.pokeapi.pokekotlin.model.NamedApiResource
import co.pokeapi.pokekotlin.model.Pokemon
import co.pokeapi.pokekotlin.model.PokemonSprites
import co.pokeapi.pokekotlin.model.PokemonType as Type

/**
 * Fake implementation of IPokemonRepository for testing purposes.
 *
 * Returns a preconfigured Pokémon object to simulate repository behavior without external dependencies.
 */
class FakePokemonRepository : IPokemonRepository {

    override suspend fun getPokemon(id: Int): Pokemon? {
        return Pokemon(
            id = id,
            name = "bulbasaur",
            baseExperience = 64,
            height = 7,
            isDefault = true,
            order = 1,
            weight = 69,
            species = NamedApiResource("bulbasaur", "1"),
            abilities = emptyList(),
            forms = emptyList(),
            gameIndices = emptyList(),
            heldItems = emptyList(),
            moves = emptyList(),
            stats = emptyList(),
            types = listOf(Type(slot = 1, type = NamedApiResource("grass", "1"))),
            sprites = PokemonSprites(
                null,
                null,
                "https://img.com/bulba.png",
                null,
                null,
                null,
                null,
                null
            )
        )
    }
}