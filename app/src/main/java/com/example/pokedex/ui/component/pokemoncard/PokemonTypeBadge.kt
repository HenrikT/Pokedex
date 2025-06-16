import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.util.PokemonUtils

/**
 * Displays a single Pokémon type as a colored badge.
 *
 * The badge uses the official Pokémon type color from [PokemonUtils.getTypeColor]
 * and shows the capitalized type name in bold white text.
 *
 * @param type The Pokémon type name (e.g. "fire", "water").
 * @param modifier Optional [Modifier] for additional layout customization.
 */
@Composable
fun PokemonTypeBadge(type: String, modifier: Modifier = Modifier) {
    val typeColor = PokemonUtils.getTypeColor(type)

    Box(
        modifier = modifier
            .background(color = typeColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}