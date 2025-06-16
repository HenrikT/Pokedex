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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.util.PokemonUtils
import com.example.pokedex.util.PokemonUtils.getId
import com.example.pokedex.util.PokemonUtils.getName

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
fun PokemonTile(navController: NavController, pokemon: PokemonDetail) {
    val types = pokemon.types.map { it.type.name }

    // Dual-type Pokémon use a gradient background from first to second type color
    val backgroundBrush = when (types.size) {
        2 -> Brush.linearGradient(
            colors = listOf(
                PokemonUtils.getTypeColor(types[0]),
                PokemonUtils.getTypeColor(types[1])
            ),
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
        )

        else -> null
    }

    // Fallback solid background for single-type Pokémon
    val backgroundColor = PokemonUtils.getTypeColor(types.firstOrNull() ?: "")

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
                .background(
                    brush = backgroundBrush ?: Brush.verticalGradient(
                        listOf(backgroundColor, backgroundColor)
                    )
                )
                .padding(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Header row with Pokémon name and ID
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

                // Pokémon sprite image
                Image(
                    painter = rememberAsyncImagePainter(pokemon.imageUrl),
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