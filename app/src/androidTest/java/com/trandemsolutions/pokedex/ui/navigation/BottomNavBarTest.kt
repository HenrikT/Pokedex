package com.trandemsolutions.pokedex.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.trandemsolutions.pokedex.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BottomNavBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun bottomNavigation_navigatesToCorrectScreens() {
        composeTestRule.onNodeWithTag("FeaturedScreen").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Pokémon").performClick()
        composeTestRule.onNodeWithTag("MyPokemonScreen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pokédex").performClick()
        composeTestRule.onNodeWithTag("PokedexScreen").assertIsDisplayed()
    }
}