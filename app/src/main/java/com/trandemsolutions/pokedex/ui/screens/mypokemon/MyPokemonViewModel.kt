package com.trandemsolutions.pokedex.ui.screens.mypokemon

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.data.MyPokemonManager
import com.trandemsolutions.pokedex.data.parseCaughtId
import com.trandemsolutions.pokedex.model.PokemonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel backing the "My Pokémon" screen.
 *
 * Observes saved Pokémon IDs and maps them to [PokemonModel] objects with shiny flags.
 * The result is stored as state to drive the UI.
 *
 * @property pokemonService The service used to retrieve Pokémon models.
 */
@HiltViewModel
class MyPokemonViewModel @Inject constructor(
    private val pokemonService: IPokemonService
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPokemonUiState())
    val uiState: StateFlow<MyPokemonUiState> = _uiState

    /**
     * Starts observing caught Pokémon from persistent storage.
     *
     * Maps raw entries to Pokémon models and updates UI state.
     *
     * @param context The context used to access persistent catch state.
     */
    fun observeCaughtPokemon(context: Context) {
        viewModelScope.launch {
            MyPokemonManager.getCaughtPokemon(context).collectLatest { idSet ->
                val mapped = idSet.mapNotNull { raw ->
                    val parsed = parseCaughtId(raw)
                    parsed?.let { idWithFlag ->
                        pokemonService.getModel(idWithFlag.id)?.let { model ->
                            model to idWithFlag.isShiny
                        }
                    }
                }
                _uiState.value = MyPokemonUiState(
                    pokemonList = mapped,
                    isLoading = false
                )
            }
        }
    }
}

/**
 * UI state for the "My Pokémon" screen.
 *
 * @property pokemonList A list of caught Pokémon models with shiny flags.
 * @property isLoading True if the screen is waiting for data.
 */
data class MyPokemonUiState(
    val pokemonList: List<Pair<PokemonModel, Boolean>> = emptyList(),
    val isLoading: Boolean = true
)