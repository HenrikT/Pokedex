package com.example.pokedex.ui.navigation

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
 * @param selected The route of the currently selected tab.
 * @param onTabSelected Callback triggered when a tab is selected. Receives the selected route.
 */
@Composable
fun BottomNavBar(
    selected: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Library,
        BottomNavItem.Bookmarks
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selected == item.route,
                onClick = { onTabSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = { Text(item.label) },
                modifier = Modifier.semantics {
                    contentDescription = "Go to ${item.label} screen"
                }
            )
        }
    }
}