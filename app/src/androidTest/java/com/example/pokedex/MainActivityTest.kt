package com.example.pokedex

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNav_clickingTabsNavigatesToCorrectScreen() {
        // Initial screen is Home
        composeTestRule.onNodeWithText("Home Screen").assertExists()

        // Navigate to Library
        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithText("Library Screen").assertExists()

        // Navigate to Bookmarks
        composeTestRule.onNodeWithText("Bookmarks").performClick()
        composeTestRule.onNodeWithText("Bookmarks Screen").assertExists()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.onNodeWithText("Home Screen").assertExists()
    }
}