package com.example.alcoholtracker.domain.logic.handlers

import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.data.remote.BeerRemoteSource
import com.example.alcoholtracker.domain.logic.DrinkCategoryHandler
import com.example.alcoholtracker.domain.model.DrinkUnit
import javax.inject.Inject

class MixedShotHandler @Inject constructor(
    private val source: BeerRemoteSource
) : DrinkCategoryHandler {
    override suspend fun fetchSuggestions(query: String): List<Drink> {
        TODO("Not yet implemented")
    }

    override fun getUnitOptions(): List<DrinkUnit> {
        TODO("Not yet implemented")
    }
}