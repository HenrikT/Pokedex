package com.example.pokedex.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.pokedex.R

/**
 * A composable button styled as a Poké Ball for toggling Pokémon in "My Pokémon".
 *
 * Plays a short scale and rotation animation to simulate catching or releasing a Pokémon.
 * Changes color based on whether the Pokémon is already caught.
 *
 * @param isCaught Whether the Pokémon is currently in "My Pokémon".
 * @param onToggleCatch Callback triggered to toggle caught status.
 */
@Composable
fun PokeBallButton(
    isCaught: Boolean,
    onToggleCatch: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Runs the bounce and twist animation when the button is tapped
    fun playCatchAnimation() {
        scope.launch {
            scale.animateTo(0.85f, animationSpec = tween(100))
            rotation.animateTo(15f, tween(100))
            rotation.animateTo(-15f, tween(100))
            rotation.animateTo(0f, tween(100))
            scale.animateTo(1f, tween(150))
        }
    }

    Surface(
        shape = CircleShape,
        shadowElevation = 6.dp,
        color = if (isCaught) Color(0xFFFFC107) else Color(0xFFB0BEC5),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                playCatchAnimation()
                onToggleCatch()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .scale(scale.value)
                .rotate(rotation.value)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ball),
                contentDescription = "Poké Ball",
                modifier = Modifier
                    .size(36.dp)
                    .scale(1.5f)
            )
        }
    }
}