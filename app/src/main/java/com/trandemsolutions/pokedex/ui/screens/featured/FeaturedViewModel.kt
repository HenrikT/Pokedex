package com.trandemsolutions.pokedex.ui.screens.featured

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.data.MyPokemonManager.isCaught
import com.trandemsolutions.pokedex.data.MyPokemonManager.isCaughtShiny
import com.trandemsolutions.pokedex.data.MyPokemonManager.toggleCaught
import com.trandemsolutions.pokedex.model.PokemonModel
import com.trandemsolutions.pokedex.util.PokemonUtils.generateRandomId
import com.trandemsolutions.pokedex.util.PokemonUtils.isShinyEncounter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Holds UI state for the Featured screen.
 *
 * Includes the current Pokémon ID, Pokémon data model, catch status, and shiny flag.
 *
 * @property pokemonId The ID of the current featured Pokémon.
 * @property pokemon The preview data for the current Pokémon.
 * @property isCaught Indicates whether the Pokémon is marked as caught.
 * @property isShiny Indicates whether the encounter is shiny.
 */
data class FeaturedUiState(
    val pokemonId: Int = generateRandomId(),
    val pokemon: PokemonModel? = null,
    val isCaught: Boolean = false,
    val isShiny: Boolean = false
)

/**
 * ViewModel for managing state in the Featured screen.
 *
 * Generates random Pokémon, checks catch/shiny status, and updates UI state accordingly.
 *
 * This ViewModel is Hilt-enabled and receives its dependencies through constructor injection.
 */
@HiltViewModel
class FeaturedViewModel @Inject constructor(
    pokemonService: IPokemonService
) : ViewModel() {

    /**
     * All Pokémon models cached at app startup.
     *
     * Used to provide fast access to Pokémon data during rerolls without additional lookups.
     */
    private val allPokemon = pokemonService.getAllModels()

    /**
     * Caches shiny encounter results for each Pokémon ID.
     *
     * Ensures consistent shiny appearance across re-renders during the same session.
     */
    private val shinyMap = mutableMapOf<Int, Boolean>()

    /**
     * Backing state for the Featured screen.
     *
     * Stores the current Pokémon ID, model, and UI-relevant flags.
     */
    private val _uiState = MutableStateFlow(FeaturedUiState())

    /**
     * Exposed immutable UI state stream for Composables to observe.
     */
    val uiState: StateFlow<FeaturedUiState> = _uiState

    /**
     * Loads Pokémon data and updates shiny/caught status based on the current ID.
     *
     * This is invoked when a new Pokémon is featured or the screen is restored.
     *
     * @param context The context used to access persistent catch state.
     */
    fun loadPokemonState(context: Context) {
        viewModelScope.launch {
            val id = _uiState.value.pokemonId
            val pokemon = allPokemon.firstOrNull { it.id == id } ?: return@launch

            val caught = isCaught(context, id)
            val shiny = if (caught) {
                isCaughtShiny(context, id)
            } else {
                shinyMap[id] ?: isShinyEncounter().also { shinyMap[id] = it }
            }

            _uiState.update {
                it.copy(
                    pokemon = pokemon,
                    isCaught = caught,
                    isShiny = shiny
                )
            }
        }
    }

    /**
     * Toggles the catch status of the current Pokémon.
     *
     * Also re-evaluates the caught state after updating it.
     *
     * @param context The context used to update and read catch status.
     */
    fun toggleCatch(context: Context) {
        val id = _uiState.value.pokemon?.id ?: return

        viewModelScope.launch {
            toggleCaught(context, id, _uiState.value.isShiny)
            val updatedCaught = isCaught(context, id)

            _uiState.update {
                it.copy(isCaught = updatedCaught)
            }
        }
    }

    /**
     * Generates a new random Pokémon ID and resets the Pokémon data.
     *
     * Used when the user wants to feature another random Pokémon.
     */
    fun generateRandomPokemon() {
        _uiState.update {
            it.copy(
                pokemonId = generateRandomId(),
                pokemon = null
            )
        }
    }
}