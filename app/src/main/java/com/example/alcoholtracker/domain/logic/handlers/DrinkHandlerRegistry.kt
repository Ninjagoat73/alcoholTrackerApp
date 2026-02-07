package com.example.alcoholtracker.domain.logic.handlers

import javax.inject.Inject

class DrinkHandlerRegistry @Inject constructor(
    val beerHandler: BeerHandler,
    val wineHandler: WineHandler,
    val cocktailHandler: CocktailHandler,
    val spiritHandler: SpiritHandler,
    val otherHandler: OtherHandler
)