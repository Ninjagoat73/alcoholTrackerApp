package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.logic.handlers.DrinkHandlerRegistry
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val drinkDao: DrinkDao,
    private val handlerRegistry: DrinkHandlerRegistry
) : ViewModel() {

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    fun fetchDrinkNames(query: String) {
        viewModelScope.launch {
            _suggestions.value = drinkDao.getDrinkNames(query)
        }
    }
    val drinks: StateFlow<List<Drink>> = drinkDao.getAllDrinks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addDrink(drink: Drink) {
        viewModelScope.launch {
            drinkDao.insertDrinks(listOf(drink))
        }
    }

    fun getDrinksForCategory(category: DrinkCategory): List<Drink> {
        return when (category) {
            DrinkCategory.BEER -> handlerRegistry.beerHandler.fetchSuggestions()
            DrinkCategory.WINE -> handlerRegistry.wineHandler.fetchSuggestions()
            DrinkCategory.SPIRIT -> handlerRegistry.spiritHandler.fetchSuggestions()
            DrinkCategory.COCKTAIL -> handlerRegistry.cocktailHandler.fetchSuggestions()
            DrinkCategory.CIDER -> handlerRegistry.ciderHandler.fetchSuggestions()
            DrinkCategory.LIQUEUR -> handlerRegistry.liquerHandler.fetchSuggestions()
            DrinkCategory.MIXED_SHOT -> handlerRegistry.mixedShotHandler.fetchSuggestions()
            DrinkCategory.OTHER -> handlerRegistry.otherHandler.fetchSuggestions()
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