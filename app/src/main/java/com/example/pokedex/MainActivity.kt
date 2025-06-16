package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.navigation.BottomNavBar
import com.example.pokedex.ui.navigation.BottomNavItem
import com.example.pokedex.ui.screens.BookmarksScreen
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.ui.screens.LibraryScreen
import com.example.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            selected = navController.currentDestination?.route ?: BottomNavItem.Home.route,
                            onTabSelected = { navController.navigate(it) }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Home.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Home.route) { HomeScreen() }
                        composable(BottomNavItem.Library.route) { LibraryScreen() }
                        composable(BottomNavItem.Bookmarks.route) { BookmarksScreen() }
                    }
                }
            }
        }
    }
}