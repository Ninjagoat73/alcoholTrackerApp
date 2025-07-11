package com.example.alcoholtracker.domain.usecase

import com.example.alcoholtracker.data.model.DrinkCategory
import java.time.LocalDateTime

data class DrinkCreateRequest(
    val logId: Int?,
    val name: String,
    val category: DrinkCategory,
    val abv: Double?,
    val volume: Double?,
    val cost: Double?,
    val recipient: String?,
    val dateTime: LocalDateTime?)
