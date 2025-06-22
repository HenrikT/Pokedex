package com.trandemsolutions.pokedex.ui.component.flavortextbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.util.PokemonUtils.getEnglishFlavorText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for loading and exposing Pokédex flavor text.
 *
 * Uses [IPokemonService] to fetch species data and exposes cleaned English flavor text.
 */
@HiltViewModel
class FlavorTextViewModel @Inject constructor(
    private val pokemonService: IPokemonService
) : ViewModel() {

    private val _flavorText = MutableStateFlow<String?>(null)
    val flavorText: StateFlow<String?> = _flavorText

    /**
     * Loads and stores the English Pokédex flavor text for a given Pokémon ID.
     *
     * @param pokemonId The ID of the Pokémon to load flavor text for.
     */
    fun loadFlavorText(pokemonId: Int) {
        viewModelScope.launch {
            val species = pokemonService.getSpecies(pokemonId)
            _flavorText.value = species?.let { getEnglishFlavorText(it.flavorTextEntries) }
        }
    }
}