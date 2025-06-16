package com.example.pokedex.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNav_displaysAllThreeTabs() {
        composeTestRule.setContent {
            BottomNavBar(selected = "home", onTabSelected = {})
        }

        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Library").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bookmarks").assertIsDisplayed()
    }

    @Test
    fun bottomNav_tabClickCallsCallback() {
        var selectedTab: String? = null
        composeTestRule.setContent {
            BottomNavBar(selected = "home", onTabSelected = { selectedTab = it })
        }

        composeTestRule.onNodeWithText("Library").performClick()
        assert(selectedTab == "library")
    }

    @Test
    fun bottomNav_itemsHaveAccessibleLabels() {
        composeTestRule.setContent {
            BottomNavBar(selected = "home", onTabSelected = {})
        }

        composeTestRule
            .onNodeWithContentDescription("Go to Home screen")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Go to Library screen")
            .assertExists()

        composeTestRule
            .onNodeWithContentDescription("Go to Bookmarks screen")
            .assertExists()
    }
}