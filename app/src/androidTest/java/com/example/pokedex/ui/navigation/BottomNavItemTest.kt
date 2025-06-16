package com.example.pokedex.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class BottomNavItemTest {

    @Test
    fun bottomNav_itemsContainExpectedTabs() {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Library,
            BottomNavItem.Bookmarks
        )

        assertEquals(3, items.size)

        assertEquals("home", items[0].route)
        assertEquals("Home", items[0].label)

        assertEquals("library", items[1].route)
        assertEquals("Library", items[1].label)

        assertEquals("bookmarks", items[2].route)
        assertEquals("Bookmarks", items[2].label)
    }
}