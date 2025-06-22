package com.trandemsolutions.pokedex.data

import com.trandemsolutions.pokedex.util.PokemonUtils.getEnglishFlavorText
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
        val models = service.getAllModels()
        assertTrue(models.isEmpty())
    }

    @Test
    fun preloadModels_populatesListCorrectly() = runTest {
        val progress = mutableListOf<Int>()

        service.preloadModelsWithProgress(total = 1) {
            progress.add(it)
        }

        val models = service.getAllModels()
        assertEquals(1, models.size)
        assertEquals("bulbasaur", models.first().name)
        assertTrue(progress.contains(1))
    }

    @Test
    fun getModel_returnsExpectedModel() = runTest {
        val model = service.getModel(1)

        assertNotNull(model)
        assertEquals(1, model!!.id)
        assertEquals("bulbasaur", model.name)
        assertTrue(model.spriteUrls.frontDefault?.contains("bulba") == true)
    }

    @Test
    fun getPokemon_returnsPokemon() = runTest {
        val pokemon = service.getPokemon(1)

        assertNotNull(pokemon)
        assertEquals("bulbasaur", pokemon!!.name)
    }

    @Test
    fun getSpecies_returnsSpeciesWithExpectedFlavorText() = runTest {
        val species = service.getSpecies(1)

        assertNotNull(species)
        val entry = getEnglishFlavorText(species!!.flavorTextEntries)
        assertTrue(entry.contains("seed", ignoreCase = true))
    }
}