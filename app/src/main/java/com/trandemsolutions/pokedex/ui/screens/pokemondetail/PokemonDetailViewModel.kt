package com.trandemsolutions.pokedex.ui.screens.pokemondetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.data.MyPokemonManager
import com.trandemsolutions.pokedex.model.PokemonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Pokémon detail screen.
 *
 * @property pokemon The Pokémon model, or null if not found.
 * @property isCaught Whether the Pokémon is currently marked as caught.
 * @property isShiny Whether the Pokémon is shiny.
 * @property showCatchButton Whether to show the catch/release button.
 */
data class PokemonDetailUiState(
    val pokemon: PokemonModel? = null,
    val isCaught: Boolean = false,
    val isShiny: Boolean = false,
    val showCatchButton: Boolean = false
)

/**
 * ViewModel for the Pokémon detail screen.
 *
 * Loads Pokémon model and state and manages catch/release logic.
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonService: IPokemonService
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState

    /**
     * Loads Pokémon data and updates the UI state.
     *
     * @param context Used to check catch status.
     * @param pokemonId ID of the Pokémon to load.
     * @param cameFromMyPokemon Whether to show the catch/release button.
     */
    fun load(context: Context, pokemonId: Int, cameFromMyPokemon: Boolean) {
        viewModelScope.launch {
            val pokemon = pokemonService.getAllModels().firstOrNull { it.id == pokemonId }
            if (pokemon != null) {
                val caught = MyPokemonManager.isCaught(context, pokemonId)
                val shiny = if (cameFromMyPokemon) MyPokemonManager.isCaughtShiny(
                    context,
                    pokemonId
                ) else false

                _uiState.update {
                    it.copy(
                        pokemon = pokemon,
                        isCaught = caught,
                        isShiny = shiny,
                        showCatchButton = cameFromMyPokemon
                    )
                }
            }
        }
    }

    /**
     * Toggles the caught state of the current Pokémon.
     *
     * @param context Used to update and read persisted caught state.
     */
    fun toggleCatch(context: Context) {
        val id = _uiState.value.pokemon?.id ?: return
        viewModelScope.launch {
            MyPokemonManager.toggleCaught(context, id, _uiState.value.isShiny)
            val updated = MyPokemonManager.isCaught(context, id)
            _uiState.update { it.copy(isCaught = updated) }
        }
    }
}