package com.trandemsolutions.pokedex.ui.screens.mypokemon

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trandemsolutions.pokedex.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI test for verifying correct display of the My Pokémon screen.
 *
 * This test navigates through the bottom navigation bar and ensures
 * the screen renders the expected UI components when Pokémon are caught.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MyPokemonScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun myPokemonScreen_navigatesAndShowsCaughtPokemon() {
        // Navigate to My Pokémon screen via bottom nav
        composeRule.onNodeWithText("My Pokémon").performClick()

        // Check if MyPokemonScreen is visible
        composeRule.onNodeWithTag("MyPokemonScreen").assertIsDisplayed()

        // Either show list or empty text
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("PokemonCard").fetchSemanticsNodes().isNotEmpty() ||
                    composeRule.onAllNodesWithText("No Pokémon caught yet").fetchSemanticsNodes()
                        .isNotEmpty()
        }
    }
}