package com.trandemsolutions.pokedex.ui.screens.pokemondetail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trandemsolutions.pokedex.TestActivity
import com.trandemsolutions.pokedex.data.MyPokemonManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PokemonDetailScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        runBlocking {
            MyPokemonManager.catchPokemon(context, 1, isShiny = false)
        }
    }

    @Test
    fun pokemonDetailScreen_rendersWithoutCrash() {
        composeRule.setContent {
            PokemonDetailScreen(
                navController = rememberNavController(),
                pokemonId = 1
            )
        }

        composeRule.onNodeWithTag("PokemonDetailScreen").assertIsDisplayed()
    }
}