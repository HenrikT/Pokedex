package com.trandemsolutions.pokedex.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.trandemsolutions.pokedex.MainActivity
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavigation_navigatesToCorrectScreens() {
        // Start on Featured
        composeTestRule.onNodeWithTag("FeaturedScreen").assertIsDisplayed()

        // Navigate to My Pokémon
        composeTestRule.onNodeWithText("My Pokémon").performClick()
        composeTestRule.onNodeWithTag("MyPokemonScreen").assertIsDisplayed()

        // Navigate to Pokédex
        composeTestRule.onNodeWithText("Pokédex").performClick()
        composeTestRule.onNodeWithTag("PokedexScreen").assertIsDisplayed()
    }
}