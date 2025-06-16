package com.example.pokedex.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PokemonServiceTest {

    private lateinit var service: IPokemonService
    private lateinit var fakeRepo: FakePokemonRepository

    @Before
    fun setUp() {
        fakeRepo = FakePokemonRepository()
        service = PokemonService(fakeRepo)
    }

    @Test
    fun getRandomPokemon_returnsValidPokemonData() = runTest {
        val result = service.getRandomPokemon()

        assertNotNull(result)
        assertEquals("bulbasaur", result.name)
        assertTrue(result.imageUrl.contains("bulba"))
    }
}