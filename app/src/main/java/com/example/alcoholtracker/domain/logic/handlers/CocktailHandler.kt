package com.example.alcoholtracker.domain.logic.handlers

import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.data.remote.BeerRemoteSource
import com.example.alcoholtracker.domain.logic.DrinkCategoryHandler
import com.example.alcoholtracker.domain.model.DrinkUnit
import javax.inject.Inject

class CocktailHandler @Inject constructor(
    private val source: BeerRemoteSource
) : DrinkCategoryHandler {
    override suspend fun fetchSuggestions(query: String): List<Drink> {
        TODO("Not yet implemented")
    }

    override fun getUnitOptions(): List<DrinkUnit> {
        return listOf(
            DrinkUnit("Standard Cocktail (150ml)", 150),
            DrinkUnit("Long Drink (300 ml)", 300),
            DrinkUnit("Mixed Shot/Shooter (60 ml)", 60),
            DrinkUnit("milliliters", 1)
        )
    }
}