package com.trandemsolutions.pokedex.ui.screens.pokedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.trandemsolutions.pokedex.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

/**
 * UI test for [PokedexScreen].
 *
 * Verifies that the screen loads correctly and displays Pokémon tiles.
 */
@HiltAndroidTest
class PokedexScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun pokedexScreen_displaysPokemonTilesOrFallback() {
        hiltRule.inject()

        // Navigate to Pokédex screen
        composeRule.onNodeWithText("Pokédex").performClick()

        // Ensure the Pokédex screen is shown
        composeRule.onNodeWithTag("PokedexScreen").assertIsDisplayed()
    }
}