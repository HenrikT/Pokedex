package com.example.pokedex.ui.component.pokemontile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.util.PokemonUtils.getId
import com.example.pokedex.util.PokemonUtils.getName
import com.example.pokedex.util.PokemonUtils.getTypeBackground

/**
 * Displays a Pokémon card with name, ID, and sprite image.
 *
 * This is used in grid layouts like "My Pokémon" to present individual Pokémon in a compact tile format.
 * Background color reflects Pokémon's type(s) for visual theming.
 *
 * @param navController Used to navigate to the Pokémon detail view when the tile is tapped.
 * @param pokemon The Pokémon to render in this tile.
 */
@Composable
fun PokemonTile(navController: NavController, pokemon: PokemonModel) {
    val typeNames = pokemon.types.map { it.type.name }
    val background = getTypeBackground(typeNames)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("pokemon/${pokemon.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(background)
                .padding(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = getName(pokemon),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = getId(pokemon),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Image(
                    painter = rememberAsyncImagePainter(pokemon.spriteUrl),
                    contentDescription = "${getName(pokemon)} image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}