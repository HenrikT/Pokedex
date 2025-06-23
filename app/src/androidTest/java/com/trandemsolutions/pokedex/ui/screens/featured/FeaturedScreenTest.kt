package com.trandemsolutions.pokedex.ui.screens.featured

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.trandemsolutions.pokedex.MainActivity
import com.trandemsolutions.pokedex.data.IPokemonService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FeaturedScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var service: IPokemonService

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            service.preloadModelsWithProgress(1) {}
        }
    }

    @Test
    fun featuredScreen_displaysPokemonCard() {
        composeRule.onNodeWithTag("FeaturedScreen").assertIsDisplayed()
    }

    @Test
    fun featuredScreen_displaysDieButton() {
        composeRule.onNodeWithTag("DieButton").assertIsDisplayed()
    }

    @Test
    fun featuredScreen_displaysPokeBallButton() {
        composeRule.onNodeWithTag("PokeBallButton").assertIsDisplayed()
    }
}