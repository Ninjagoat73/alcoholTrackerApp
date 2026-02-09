package com.example.alcoholtracker.domain.usecase

import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import java.time.LocalDateTime

data class DrinkCreateRequest(
    val logId: Int?,
    val name: String,
    val category: DrinkCategory,
    val abv: Double?,
    val volume: Int,
    val cost: Double?,
    val recipient: String?,
    val inputAmount: Double?,
    val drinkUnit: DrinkUnit?,
    val dateTime: LocalDateTime?
)
