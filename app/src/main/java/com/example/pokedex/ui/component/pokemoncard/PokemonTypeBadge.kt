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
import co.pokeapi.pokekotlin.model.PokemonType
import com.example.pokedex.util.PokemonUtils

/**
 * Displays a single Pokémon type as a colored badge.
 *
 * Uses the official Pokémon type color from [PokemonUtils.getTypeColor] based on the
 * provided [PokemonType] and renders the capitalized name in bold white text.
 *
 * @param type The full [PokemonType] object, including the type name.
 * @param modifier Optional [Modifier] for layout customization.
 */
@Composable
fun PokemonTypeBadge(type: PokemonType, modifier: Modifier = Modifier) {
    val typeName = type.type.name
    val typeColor = PokemonUtils.getTypeColor(typeName)

    Box(
        modifier = modifier
            .background(color = typeColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = typeName.replaceFirstChar { it.uppercase() },
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}