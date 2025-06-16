package com.example.pokedex.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class BottomNavItemTest {

    @Test
    fun bottomNav_itemsContainExpectedTabs() {
        val items = listOf(
            BottomNavItem.Featured,
            BottomNavItem.Pokedex,
            BottomNavItem.MyPokemon
        )

        assertEquals(3, items.size)

        assertEquals("featured", items[0].route)
        assertEquals("Featured", items[0].label)

        assertEquals("pokedex", items[1].route)
        assertEquals("Pokédex", items[1].label)

        assertEquals("myPokemon", items[2].route)
        assertEquals("My Pokémon", items[2].label)
    }
}