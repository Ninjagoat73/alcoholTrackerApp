package com.example.alcoholtracker.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.data.repository.DrinkRepository
import com.example.alcoholtracker.domain.logic.handlers.DrinkHandlerRegistry
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import com.vamsi.snapnotify.SnapNotify
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DrinkUiState(
    val localDrinks: List<Drink> = emptyList(),
    val suggestions: List<Drink> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val repository: DrinkRepository,
    private val handlerRegistry: DrinkHandlerRegistry
) : ViewModel() {
    private val _uiState = MutableStateFlow(DrinkUiState())
    val uiState: StateFlow<DrinkUiState> = _uiState.asStateFlow()

    fun loadLocalDrinks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}
            try {
                val drinks = repository.getAllDrinks()
                _uiState.update {
                    it.copy(localDrinks = drinks, isLoading = false)
                }
            }
            catch (e: Exception){
                _uiState.update {
                    it.copy(errorMessage = "Failed to load drinks", isLoading = false)
                }
                SnapNotify.showError("Failed to load local drinks.")
            }

        }
    }

    fun getAllRecipients() {
        viewModelScope.launch {

        }
    }

    fun searchDrinks(query: String, category: DrinkCategory)  {
        viewModelScope.launch {

            val currentLocal = _uiState.value.localDrinks
            val localResults = currentLocal.filter {
                it.category == category.nameString && it.name.contains(query, ignoreCase = true)
            }

            val finalResults = if (localResults.size < 5) {
                val remoteResults = repository.searchApiDrinks(query, category)
                localResults + remoteResults
            } else {
                localResults
            }

            _uiState.update { it.copy(suggestions = finalResults) }
        }
    }

    fun getDrinkUnitsForCategory(category: DrinkCategory): List<DrinkUnit>{
        return when (category) {
            DrinkCategory.BEER -> handlerRegistry.beerHandler.getUnitOptions()
            DrinkCategory.WINE -> handlerRegistry.wineHandler.getUnitOptions()
            DrinkCategory.SPIRIT -> handlerRegistry.spiritHandler.getUnitOptions()
            DrinkCategory.COCKTAIL -> handlerRegistry.cocktailHandler.getUnitOptions()
            DrinkCategory.OTHER -> handlerRegistry.otherHandler.getUnitOptions()
        }
    }

}
