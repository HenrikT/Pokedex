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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.model.PokemonModel
import com.example.pokedex.util.PokemonUtils.getId
import com.example.pokedex.util.PokemonUtils.getName
import com.example.pokedex.util.PokemonUtils.getTypeBackground

/**
 * Displays a small Pokémon tile with sprite, name, and ID.
 *
 * Used in dense grids such as the Pokédex screen. The tile shows the Pokémon’s
 * front-facing sprite, name, and ID over a background themed to its type(s).
 *
 * @param pokemon The [PokemonModel] instance to display.
 * @param navController Used to navigate to the Pokémon detail view on tap.
 */
@Composable
fun PokemonTileSmall(pokemon: PokemonModel, navController: NavController) {
    val typeNames = pokemon.types.map { it.type.name }
    val background = getTypeBackground(typeNames)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                navController.navigate("pokemon/${pokemon.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) {
            Text(
                text = getId(pokemon),
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 4.dp)
            )

            Image(
                painter = rememberAsyncImagePainter(pokemon.spriteUrl),
                contentDescription = "${getName(pokemon)} image",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.85f)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            Text(
                text = getName(pokemon),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomCenter),
                textAlign = TextAlign.Center
            )
        }
    }
}