package com.example.shared.utils

import kotlinx.datetime.LocalDate


fun getFormattedDate(current: LocalDate): String{
    val month = current.month.toString()
        .replaceFirstChar {it.titlecase() }
    val day = current.dayOfMonth
    val suffix = getSuffix(day)

    return "$month $day$suffix"
}

fun getSuffix(day: Int): String{
    return when{
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}