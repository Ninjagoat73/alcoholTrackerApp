package com.example.alcoholtracker.domain.logic.handlers

import android.content.Context
import com.example.alcoholtracker.data.remote.Beer
import com.example.alcoholtracker.data.remote.BeerRemoteSource
import com.example.alcoholtracker.domain.logic.DrinkCategoryHandler
import com.example.alcoholtracker.domain.model.DrinkUnit

object BeerHandler : DrinkCategoryHandler<Beer> {

    private var source: BeerRemoteSource? = null

    fun init(context: Context){
        source = BeerRemoteSource(context)
    }

    override suspend fun fetchSuggestions(): List<Beer> {
       return source?.getBeers() ?: throw IllegalStateException("Nothing to get beer :(")
    }

    override fun getUnitOptions(): List<DrinkUnit> {
        return listOf(
            DrinkUnit("Can (500 ml)", 500),
            DrinkUnit("Small Bottle (300 ml)", 300),
            DrinkUnit("Bottle (330 ml)", 330),
            DrinkUnit("milliliters", null)
        )
    }
}