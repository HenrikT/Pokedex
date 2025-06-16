package com.example.pokedex.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * Bottom navigation bar used throughout the app.
 *
 * Displays three navigation items: Home, Library, and Bookmarks.
 *
 * @param selected The ID of the currently selected tab ("home", "library", or "bookmarks").
 * @param onTabSelected Callback triggered when a tab is selected. Receives the selected tab ID.
 */
@Composable
fun BottomNavBar(
    selected: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == "home",
            onClick = { onTabSelected("home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            modifier = Modifier.semantics {
                contentDescription = "Go to Home screen"
            }
        )
        NavigationBarItem(
            selected = selected == "library",
            onClick = { onTabSelected("library") },
            icon = { Icon(Icons.Filled.Search, contentDescription = null) },
            label = { Text("Library") },
            modifier = Modifier.semantics {
                contentDescription = "Go to Library screen"
            }
        )
        NavigationBarItem(
            selected = selected == "bookmarks",
            onClick = { onTabSelected("bookmarks") },
            icon = { Icon(Icons.Filled.Star, contentDescription = null) },
            label = { Text("Bookmarks") },
            modifier = Modifier.semantics {
                contentDescription = "Go to Bookmarks screen"
            }
        )
    }
}