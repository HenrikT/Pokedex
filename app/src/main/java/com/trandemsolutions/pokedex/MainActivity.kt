package com.trandemsolutions.pokedex

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.trandemsolutions.pokedex.ui.navigation.BottomNavBar
import com.trandemsolutions.pokedex.ui.navigation.BottomNavItem
import com.trandemsolutions.pokedex.ui.screens.featured.FeaturedScreen
import com.trandemsolutions.pokedex.ui.screens.MyPokemonScreen
import com.trandemsolutions.pokedex.ui.screens.PokedexScreen
import com.trandemsolutions.pokedex.ui.screens.PokemonDetailScreen
import com.trandemsolutions.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point of the application after the splash screen.
 *
 * Sets up the navigation graph, bottom navigation bar, and intercepts system back button
 * to preserve in-memory state like the Pokémon cache.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Initializes the Compose UI and handles system back press behavior.
     *
     * Uses [Scaffold] with a [BottomNavBar], and sets up routing between
     * Featured, Pokédex, My Pokémon, and Pokémon detail screens.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Intercept the system back press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Instead of closing the app, move it to background. This ensures the pokédex stays cached.
                moveTaskToBack(true)
            }
        })

        enableEdgeToEdge()

        setContent {
            PokedexTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Featured.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Featured.route) {
                            FeaturedScreen()
                        }
                        composable(BottomNavItem.Pokedex.route) {
                            PokedexScreen(navController)
                        }
                        composable(BottomNavItem.MyPokemon.route) {
                            MyPokemonScreen(navController)
                        }
                        composable(
                            route = "pokemon/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                            PokemonDetailScreen(
                                navController = navController,
                                pokemonId = id
                            )
                        }
                    }
                }
            }
        }
    }
}