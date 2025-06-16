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
 * Screen that displays the user's favorite Pokémon.
 *
 * Lets kids revisit their favorite Pokémon — the ones they’ve starred and saved.
 * Feels like their own hand-picked Poké-team.
 */
@Composable
fun MyPokemonScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "My Pokémon screen showing saved favorites" }
            .testTag("MyPokemonScreen")
    )
}