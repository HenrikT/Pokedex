package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController

/**
 * Pokédex screen that shows the full collection of Pokémon.
 *
 * Lets kids browse through every known Pokémon and search by name.
 * Aims to feel like their personal digital Pokédex library.
 */
@Composable
fun PokedexScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "Pokédex screen showing all Pokémon" }
            .testTag("PokedexScreen")
    )
}