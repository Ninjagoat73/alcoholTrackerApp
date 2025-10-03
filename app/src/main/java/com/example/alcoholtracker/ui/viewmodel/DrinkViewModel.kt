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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val repository: DrinkRepository,
    private val handlerRegistry: DrinkHandlerRegistry
) : ViewModel() {

    private val allLocalDrinks = mutableStateListOf<Drink>()
    private val _suggestions = MutableStateFlow<List<Drink>>(emptyList())
    val suggestions: StateFlow<List<Drink>> = _suggestions.asStateFlow()


    fun loadLocalDrinks() {
        viewModelScope.launch {
            try {
                allLocalDrinks.clear()
                allLocalDrinks.addAll(repository.getAllDrinks())
            }
            catch (e: Exception){
                SnapNotify.showError("Failed to load local drinks")
            }

        }
    }

    fun searchDrinks(query: String, category: DrinkCategory)  {
        viewModelScope.launch {

            loadLocalDrinks()

            val localResults = allLocalDrinks
                .filter { (it.category == category.nameString) && it.name.contains(query, ignoreCase = true) }

            val finalResults = if (localResults.size < 5) {
                val remoteResults = repository.searchApiDrinks(query, category)
                allLocalDrinks.addAll(remoteResults)
                (localResults + remoteResults)
            } else {
                localResults
            }

            _suggestions.value = finalResults
        }
    }

    fun getDrinkUnitsForCategory(category: DrinkCategory): List<DrinkUnit>{
        return when (category) {
            DrinkCategory.BEER -> handlerRegistry.beerHandler.getUnitOptions()
            DrinkCategory.WINE -> handlerRegistry.wineHandler.getUnitOptions()
            DrinkCategory.SPIRIT -> handlerRegistry.spiritHandler.getUnitOptions()
            DrinkCategory.COCKTAIL -> handlerRegistry.cocktailHandler.getUnitOptions()
            DrinkCategory.CIDER -> handlerRegistry.ciderHandler.getUnitOptions()
            DrinkCategory.LIQUEUR -> handlerRegistry.liquerHandler.getUnitOptions()
            DrinkCategory.MIXED_SHOT -> handlerRegistry.mixedShotHandler.getUnitOptions()
            DrinkCategory.OTHER -> handlerRegistry.otherHandler.getUnitOptions()
        }
    }

}
