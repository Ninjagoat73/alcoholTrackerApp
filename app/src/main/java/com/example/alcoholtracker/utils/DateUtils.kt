package com.example.alcoholtracker.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale


fun getFormattedDate(current: LocalDate): String {
    val month = current.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).lowercase()
        .replaceFirstChar { it.titlecase() }
    val day = current.dayOfMonth
    val suffix = getSuffix(day)

    return "$month $day$suffix"
}

fun getSuffix(day: Int): String {
    return when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}

fun getLocalDate(millis: Long?): LocalDate {
    return Instant.ofEpochMilli(millis!!)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}