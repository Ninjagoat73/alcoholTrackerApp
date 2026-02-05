package com.example.alcoholtracker.data.repository


import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.logic.handlers.DrinkHandlerRegistry
import com.example.alcoholtracker.domain.model.DrinkCategory
import javax.inject.Inject

class DrinkRepository @Inject constructor(
    private val drinkDao: DrinkDao,
    private val handlerRegistry: DrinkHandlerRegistry
) {
    suspend fun getAllDrinks(): List<Drink> = drinkDao.getAllDrinks()

    suspend fun insertDrink(drink: Drink){
        drinkDao.insertDrink(drink)
    }



    suspend fun searchApiDrinks(query: String, category: DrinkCategory) : List<Drink>{
        return when (category) {
            DrinkCategory.BEER -> handlerRegistry.beerHandler.fetchSuggestions(query)
            DrinkCategory.WINE -> handlerRegistry.wineHandler.fetchSuggestions(query)
            DrinkCategory.SPIRIT -> handlerRegistry.spiritHandler.fetchSuggestions(query)
            DrinkCategory.COCKTAIL -> handlerRegistry.cocktailHandler.fetchSuggestions(query)
            DrinkCategory.CIDER -> handlerRegistry.ciderHandler.fetchSuggestions(query)
            DrinkCategory.LIQUEUR -> handlerRegistry.liquerHandler.fetchSuggestions(query)
            DrinkCategory.MIXED_SHOT -> handlerRegistry.mixedShotHandler.fetchSuggestions(query)
            DrinkCategory.OTHER -> handlerRegistry.otherHandler.fetchSuggestions(query)
        }
    }
}
