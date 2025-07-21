package com.example.alcoholtracker.domain.logic

import com.example.alcoholtracker.domain.model.DrinkUnit

interface DrinkCategoryHandler<out T> {
    suspend fun fetchSuggestions(): List<T>

    fun getUnitOptions(): List<DrinkUnit>
}
