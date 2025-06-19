package com.example.pokedex.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom navigation bar used throughout the app.
 *
 * Displays three navigation items: Featured, Pokédex, and My Pokémon.
 * Highlights the currently selected screen based on the navigation back stack.
 * Uses [BottomNavItem] definitions to configure icons and routes.
 *
 * @param navController The navigation controller used to navigate between screens.
 */
@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Featured,
        BottomNavItem.Pokedex,
        BottomNavItem.MyPokemon,
    )

    NavigationBar {
        // Get the current navigation route to determine which item is selected
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    // Render appropriate icon depending on vector or painter type
                    when (val icon = item.icon()) {
                        is BottomNavIcon.Vector -> Icon(
                            imageVector = icon.icon,
                            contentDescription = null
                        )

                        is BottomNavIcon.PainterIcon -> Icon(
                            painter = icon.painter,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(20.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                label = { Text(item.label) },
                selected = selected,
                onClick = {
                    if (!selected) {
                        // Navigate to the selected route, clearing any previous destinations
                        navController.navigate(item.route) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }
    }
}