package com.example.alcoholtracker.domain.usecase.adddrinkfuns

import android.util.Log
import com.example.alcoholtracker.domain.model.DrinkUnit
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun getFinalAmount(unit: DrinkUnit?, amount: Double?): Int {

    Log.d("DrinkUnit", "Unit: $unit, Amount: $amount")

    return if (unit != null && amount != null) {
        (unit.amount * amount).toInt()
    } else 0
}

fun getLocalDateTime(date: LocalDate, time: LocalTime): LocalDateTime {
    return LocalDateTime.of(date, time)
}

