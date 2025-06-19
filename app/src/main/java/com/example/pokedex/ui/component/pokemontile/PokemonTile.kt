package com.example.pokedex.ui.component.pokemontile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.util.PokemonUtils.getFrontSpriteUrl
import com.example.pokedex.util.PokemonUtils.getId
import com.example.pokedex.util.PokemonUtils.getName
import com.example.pokedex.util.PokemonUtils.getTypeBackground
import com.example.pokedex.util.PokemonUtils.getShinyFrontSpriteUrl

/**
 * Displays a compact tile card for a Pokémon with name, ID, and sprite.
 *
 * This is used in grid views such as "My Pokémon" or the full Pokédex list.
 * The card includes a background gradient based on the Pokémon's type(s)
 * and navigates to the detail screen when clicked.
 *
 * @param navController The navigation controller used to open the Pokémon detail page.
 * @param pokemon The [PokemonModel] instance to render in this tile.
 * @param isShiny Whether to display the shiny sprite for this Pokémon.
 */
@Composable
fun PokemonTile(navController: NavController, pokemon: PokemonModel, isShiny: Boolean) {
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

                val spriteUrl = if (isShiny) {
                    getShinyFrontSpriteUrl(pokemon)
                } else {
                    getFrontSpriteUrl(pokemon)
                }

                Image(
                    painter = rememberAsyncImagePainter(spriteUrl),
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