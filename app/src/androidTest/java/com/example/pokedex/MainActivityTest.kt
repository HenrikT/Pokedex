package com.example.pokedex

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
        composeTestRule.onNodeWithTag("HomeScreen", useUnmergedTree = true).assertExists()

        // Navigate to Library
        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithTag("LibraryScreen").assertExists()

        // Navigate to Bookmarks
        composeTestRule.onNodeWithText("Bookmarks").performClick()
        composeTestRule.onNodeWithTag("BookmarksScreen").assertExists()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.onNodeWithTag("HomeScreen").assertExists()
    }
}