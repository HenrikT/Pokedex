package com.example.pokedex.ui.navigation
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pokedex.MainActivity
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavigation_navigatesToCorrectScreens() {
        // Check that this is the default screen
        composeTestRule.onNodeWithTag("HomeScreen").assertIsDisplayed()

        // Navigate to Bookmarks
        composeTestRule.onNodeWithText("Bookmarks").performClick()
        composeTestRule.onNodeWithTag("BookmarksScreen").assertIsDisplayed()

        // Navigate to Library
        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithTag("LibraryScreen").assertIsDisplayed()
    }
}