package com.example.pokedex.ui.component.button

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import com.example.pokedex.R
import kotlinx.coroutines.launch

/**
 * A composable die-shaped button for triggering a random Pokémon.
 *
 * Plays a rotation animation when tapped to simulate dice rolling.
 *
 * @param onRoll Callback triggered to generate a new random Pokémon.
 */
@Composable
fun DieButton(
    onRoll: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun playRollAnimation() {
        scope.launch {
            rotation.animateTo(360f, animationSpec = tween(400))
            rotation.snapTo(0f)
        }
    }

    Surface(
        shape = CircleShape,
        shadowElevation = 6.dp,
        color = Color(0xFFB0BEC5),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                playRollAnimation()
                onRoll()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .rotate(rotation.value)
        ) {
            Image(
                painter = painterResource(id = R.drawable.die),
                contentDescription = "Die",
                modifier = Modifier
                    .size(48.dp)
                    .scale(2f)
            )
        }
    }
}