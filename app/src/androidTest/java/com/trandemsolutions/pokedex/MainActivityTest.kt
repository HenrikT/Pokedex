package com.trandemsolutions.pokedex

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun bottomNav_clickingTabsNavigatesToCorrectScreen() {
        // Initial screen is Featured
        composeTestRule.onNodeWithTag("FeaturedScreen", useUnmergedTree = true).assertExists()

        // Navigate to Pokédex
        composeTestRule.onNodeWithText("Pokédex").performClick()
        composeTestRule.onNodeWithTag("PokedexScreen").assertExists()

        // Navigate to My Pokémon
        composeTestRule.onNodeWithText("My Pokémon").performClick()
        composeTestRule.onNodeWithTag("MyPokemonScreen").assertExists()

        // Navigate back to Featured
        composeTestRule.onNodeWithText("Featured").performClick()
        composeTestRule.onNodeWithTag("FeaturedScreen").assertExists()
    }
}