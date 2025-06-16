package com.example.pokedex.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.theme.PokedexTheme
import org.junit.Rule
import org.junit.Test

class PokemonDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailScreen_displaysPokemonCard() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            PokedexTheme {
                PokemonDetailScreen(navController = navController, pokemonId = 1)
            }
        }

        composeTestRule.onNodeWithTag("PokemonDetailScreen").assertIsDisplayed()
    }
}