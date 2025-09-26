package com.example.alcoholtracker.domain.logic

import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkUnit

interface DrinkCategoryHandler {
    fun fetchSuggestions(query: String): List<Drink>

    fun getUnitOptions(): List<DrinkUnit>
}
