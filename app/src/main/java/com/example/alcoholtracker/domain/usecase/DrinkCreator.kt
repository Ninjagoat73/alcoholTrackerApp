package com.example.alcoholtracker.domain.usecase

import com.example.alcoholtracker.data.model.DrinkCategory
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import java.time.LocalDateTime
import kotlin.math.cos

fun onCreateDrinkClick(
    name: String,
    category: DrinkCategory,
    abv: Double?,
    volume: Double?,
    cost: Double?,
    recipient: String?,
    dateTime: LocalDateTime?,
    drinkLogViewModel: UserAndUserDrinkLogViewModel){
    val drinkRequest = DrinkCreateRequest(
        name = name,
        category = category,
        abv = abv,
        volume = volume,
        cost = cost,
        recipient = recipient,
        dateTime = dateTime
    )
    drinkLogViewModel.logDrink(drinkRequest)

}

fun editDrink(name: String, percentage: Double?, amount: Double?){}