package com.example.pokedex.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a single tab in the bottom navigation bar.
 *
 * Each tab has a unique route, a visible label, and an associated icon.
 *
 * Used by [BottomNavBar] to render selectable navigation options.
 *
 * @param route Unique ID used to identify the tab and handle selection.
 * @param label Visible name of the tab shown to users.
 * @param icon Visual icon shown alongside the label.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Library : BottomNavItem("library", "Library", Icons.Filled.Search)
    object Bookmarks : BottomNavItem("bookmarks", "Bookmarks", Icons.Filled.Star)
}