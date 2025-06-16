package com.example.pokedex.data

import android.content.Context
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PokemonServiceTest {

    private lateinit var service: IPokemonService
    private lateinit var fakeRepo: FakePokemonRepository
    private lateinit var mockMyPokemon: IMyPokemonManager
    private lateinit var context: Context

    @Before
    fun setUp() {
        fakeRepo = FakePokemonRepository()
        mockMyPokemon = mockk(relaxed = true)
        context = mockk(relaxed = true)

        service = PokemonService(fakeRepo, mockMyPokemon)
    }

    @Test
    fun getRandomPokemon_returnsValidPokemonData() = runTest {
        val result = service.getRandomPokemon()

        assertNotNull(result)
        assertEquals("bulbasaur", result.name)
        assertTrue(result.imageUrl.contains("bulba"))
    }

    @Test
    fun getPokemonDetail_returnsExpectedPokemon() = runTest {
        val result = service.getPokemonDetail(1)

        assertEquals(1, result.id)
        assertEquals("bulbasaur", result.name)
        assertTrue(result.entry.isNotBlank())
        assertTrue(result.types.isNotEmpty())
    }

    @Test
    fun isInMyPokemon_returnsTrueAfterAdding() = runTest {
        val id = 1

        coEvery { mockMyPokemon.setMyPokemon(context, id, true) } returns Unit
        coEvery { mockMyPokemon.isInMyPokemon(context, id) } returns true

        service.setMyPokemon(context, id, true)
        val result = service.isInMyPokemon(context, id)

        assertTrue(result)

        coVerify { mockMyPokemon.setMyPokemon(context, id, true) }
        coVerify { mockMyPokemon.isInMyPokemon(context, id) }
    }

    @Test
    fun isInMyPokemon_returnsFalseByDefault() = runTest {
        val id = 999
        coEvery { mockMyPokemon.isInMyPokemon(context, id) } returns false

        val result = service.isInMyPokemon(context, id)
        assertFalse(result)

        coVerify { mockMyPokemon.isInMyPokemon(context, id) }
    }

    @Test
    fun setMyPokemon_false_removesFromList() = runTest {
        val id = 1

        coEvery { mockMyPokemon.setMyPokemon(context, id, true) } returns Unit
        coEvery { mockMyPokemon.setMyPokemon(context, id, false) } returns Unit
        coEvery { mockMyPokemon.isInMyPokemon(context, id) } returns false

        service.setMyPokemon(context, id, true)
        service.setMyPokemon(context, id, false)

        val result = service.isInMyPokemon(context, id)
        assertFalse(result)

        coVerify { mockMyPokemon.setMyPokemon(context, id, true) }
        coVerify { mockMyPokemon.setMyPokemon(context, id, false) }
    }
}