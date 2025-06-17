package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.data.PokemonService
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.util.PokemonUtils.MAX_POKEMON_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Initial splash/loading screen shown on app startup.
 *
 * Preloads all Pokémon summaries in the background while showing a progress bar.
 * Once loading completes, the user is navigated to [MainActivity].
 */
class LoadingActivity : ComponentActivity() {

    /**
     * Begins the preload process and sets up the splash screen UI.
     *
     * Uses [PokemonService.preloadModelsWithProgress] to download Pokémon metadata
     * and show incremental progress on screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val total = MAX_POKEMON_ID

        setContent {
            var loadedCount by remember { mutableIntStateOf(0) }

            PokedexTheme {
                SplashScreenUI(loaded = loadedCount, total = total)
            }

            // Launch loading in background and update progress as we go
            lifecycleScope.launch {
                PokemonService.preloadModelsWithProgress(total) { loaded ->
                    loadedCount = loaded
                }

                // Brief delay before navigating to ensure user sees progress completion
                delay(300)
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    /**
     * Composable that displays the splash UI with progress updates.
     *
     * @param loaded Number of Pokémon loaded so far.
     * @param total Total number of Pokémon to preload.
     */
    @Composable
    fun SplashScreenUI(loaded: Int, total: Int) {
        val progress = loaded / total.toFloat()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Loading Pokémon…",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Loaded $loaded of $total",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}