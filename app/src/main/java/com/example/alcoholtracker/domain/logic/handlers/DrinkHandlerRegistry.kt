package com.example.alcoholtracker.domain.logic.handlers

import javax.inject.Inject

class DrinkHandlerRegistry @Inject constructor(
    val beerHandler: BeerHandler,
    val wineHandler: WineHandler,
    val ciderHandler: CiderHandler,
    val cocktailHandler: CocktailHandler,
    val liquerHandler: LiquerHandler,
    val spiritHandler: SpiritHandler,
    val mixedShotHandler: MixedShotHandler,
    val otherHandler: OtherHandler
)