package com.trandemsolutions.pokedex.ui.screens.pokemondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trandemsolutions.pokedex.ui.component.button.PokeBallButton
import com.trandemsolutions.pokedex.ui.component.pokemoncard.PokemonCard
import com.trandemsolutions.pokedex.ui.navigation.BottomNavItem

/**
 * Detail screen that displays full information about a single Pokémon.
 *
 * - Uses [PokemonDetailViewModel] to manage state.
 * - Displays a full-sized [PokemonCard].
 * - If accessed from "My Pokémon", shows a [PokeBallButton] to toggle caught state.
 *
 * @param navController The navigation controller used to detect screen origin.
 * @param pokemonId The ID of the Pokémon to show.
 * @param viewModel The ViewModel used to manage Pokémon detail state.
 */
@Composable
fun PokemonDetailScreen(
    navController: NavController,
    pokemonId: Int,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val cameFromMyPokemon = remember {
        navController.previousBackStackEntry?.destination?.route == BottomNavItem.MyPokemon.route
    }

    LaunchedEffect(pokemonId) {
        viewModel.load(context, pokemonId, cameFromMyPokemon)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("PokemonDetailScreen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            uiState.pokemon?.let {
                PokemonCard(
                    pokemon = it,
                    isShiny = uiState.isShiny,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            if (uiState.showCatchButton && uiState.pokemon != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PokeBallButton(
                        isCaught = uiState.isCaught,
                        onToggleCatch = { viewModel.toggleCatch(context) }
                    )
                }
            }
        }
    }
}