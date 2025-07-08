package com.example.alcoholtracker.di

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime


class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}