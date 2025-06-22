package com.trandemsolutions.pokedex.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents an item in the bottom navigation bar.
 *
 * Each object corresponds to a screen and defines:
 * - `route`: the navigation route for the screen.
 * - `label`: the display name shown in the navigation bar.
 * - `icon`: a composable that returns either a vector or painter icon.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> BottomNavIcon
) {
    object Featured : BottomNavItem(
        route = "featured",
        label = "Featured",
        icon = { BottomNavIcon.Vector(Icons.Filled.EmojiEvents) }
    )

    object Pokedex : BottomNavItem(
        route = "pokedex",
        label = "Pokédex",
        icon = { BottomNavIcon.Vector(Icons.Filled.Search) }
    )

    object MyPokemon : BottomNavItem(
        route = "myPokemon",
        label = "My Pokémon",
        icon = {
            BottomNavIcon.PainterIcon(
                painter = androidx.compose.ui.res.painterResource(
                    id = com.trandemsolutions.pokedex.R.drawable.ball
                )
            )
        }
    )
}

/**
 * Sealed class to represent the two types of icons used in bottom navigation:
 * - [Vector] for vector-based icons from the material icon set.
 * - [PainterIcon] for custom drawable resources.
 */
sealed class BottomNavIcon {
    data class Vector(val icon: ImageVector) : BottomNavIcon()
    data class PainterIcon(val painter: Painter) : BottomNavIcon()
}