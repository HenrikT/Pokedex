package com.trandemsolutions.pokedex.ui.screens.pokedex

import androidx.lifecycle.ViewModel
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.model.PokemonModel
import com.trandemsolutions.pokedex.util.PokemonUtils.similarity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel backing the Pokédex screen.
 *
 * Provides live state for search filtering and Pokémon data from [IPokemonService].
 * Supports fuzzy matching based on name or ID prefix.
 *
 * @property pokemonService Service used to retrieve Pokémon model data.
 */
@HiltViewModel
class PokedexViewModel @Inject constructor(
    pokemonService: IPokemonService
) : ViewModel() {

    /**
     * Current search query entered by the user.
     */
    var query: String = ""
        private set

    private val allPokemonModels = pokemonService.getAllModels()

    private val _uiState = MutableStateFlow(PokedexUiState(filteredList = allPokemonModels))
    val uiState: StateFlow<PokedexUiState> = _uiState

    /**
     * Updates the current search query and recomputes the filtered Pokémon list.
     *
     * @param newQuery The new query string entered by the user.
     */
    fun onSearchQueryChanged(newQuery: String) {
        query = newQuery
        val filtered = if (newQuery.isBlank()) {
            allPokemonModels
        } else {
            allPokemonModels.mapNotNull { model ->
                val score = when {
                    model.id.toString().startsWith(newQuery) -> 1.0
                    similarity(model.name, newQuery) >= 0.4 -> similarity(model.name, newQuery)
                    else -> null
                }
                score?.let { model to it }
            }.sortedByDescending { it.second }
                .map { it.first }
        }
        _uiState.value = PokedexUiState(filteredList = filtered)
    }
}

/**
 * UI state for the Pokédex screen.
 *
 * @property filteredList The current list of Pokémon to display based on the search query.
 */
data class PokedexUiState(
    val filteredList: List<PokemonModel>
)