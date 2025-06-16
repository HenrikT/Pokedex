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
    private lateinit var mockFavorites: IFavoritesManager
    private lateinit var context: Context

    @Before
    fun setUp() {
        fakeRepo = FakePokemonRepository()
        mockFavorites = mockk(relaxed = true)
        context = mockk(relaxed = true)

        service = PokemonService(fakeRepo, mockFavorites)
    }

    @Test
    fun getRandomPokemon_returnsValidPokemonData() = runTest {
        val result = service.getRandomPokemon()

        assertNotNull(result)
        assertEquals("bulbasaur", result.name)
        assertTrue(result.imageUrl.contains("bulba"))
    }

    @Test
    fun getPreview_returnsExpectedPokemon() = runTest {
        val result = service.getPreview(1)

        assertEquals(1, result.id)
        assertEquals("bulbasaur", result.name)
    }

    @Test
    fun isFavorite_returnsTrueAfterSettingFavorite() = runTest {
        val id = 1

        coEvery { mockFavorites.setFavorite(context, id, true) } returns Unit
        coEvery { mockFavorites.isFavorite(context, id) } returns true

        service.setFavorite(context, id, true)
        val result = service.isFavorite(context, id)

        assertTrue(result)

        coVerify { mockFavorites.setFavorite(context, id, true) }
        coVerify { mockFavorites.isFavorite(context, id) }
    }

    @Test
    fun isFavorite_returnsFalseByDefault() = runTest {
        val id = 999
        coEvery { mockFavorites.isFavorite(context, id) } returns false

        val result = service.isFavorite(context, id)
        assertFalse(result)

        coVerify { mockFavorites.isFavorite(context, id) }
    }

    @Test
    fun setFavorite_false_removesFromFavorites() = runTest {
        val id = 1

        coEvery { mockFavorites.setFavorite(context, id, true) } returns Unit
        coEvery { mockFavorites.setFavorite(context, id, false) } returns Unit
        coEvery { mockFavorites.isFavorite(context, id) } returns false

        service.setFavorite(context, id, true)
        service.setFavorite(context, id, false)

        val result = service.isFavorite(context, id)
        assertFalse(result)

        coVerify { mockFavorites.setFavorite(context, id, true) }
        coVerify { mockFavorites.setFavorite(context, id, false) }
    }
}