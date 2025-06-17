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
        service = FakePokemonService(fakeRepo)
    }

    @Test
    fun getAllModels_returnsEmptyByDefault() {
        val summaries = service.getAllModels()
        assertTrue(summaries.isEmpty())
    }

    @Test
    fun preloadSummaries_populatesListCorrectly() = runTest {
        val progress = mutableListOf<Int>()

        service.preloadModelsWithProgress(total = 1) {
            progress.add(it)
        }

        val summaries = service.getAllModels()
        assertEquals(1, summaries.size)
        assertEquals("bulbasaur", summaries.first().name)
        assertTrue(progress.contains(1))
    }

    @Test
    fun getPokemonDetail_returnsExpectedModel() = runTest {
        val detail = service.getModel(1)

        assertNotNull(detail)
        assertEquals(1, detail!!.id)
        assertEquals("bulbasaur", detail.name)
        assertTrue(detail.spriteUrl.contains("bulba"))
    }

    @Test
    fun getPokemonWithEntry_returnsValidPair() = runTest {
        val pair = service.getPokemonWithEntry(1)

        assertNotNull(pair)
        assertEquals("bulbasaur", pair!!.first.name)
        assertTrue(pair.second.contains("seed")) // match mock text
    }

    @Test
    fun getPokemon_returnsPokemon() = runTest {
        val pokemon = service.getPokemon(1)

        assertNotNull(pokemon)
        assertEquals("bulbasaur", pokemon!!.name)
    }
}