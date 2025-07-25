package com.example.alcoholtracker.di

import androidx.room.TypeConverter
import com.example.alcoholtracker.domain.model.DrinkCategory
import java.time.LocalDateTime


class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromCategory(category: DrinkCategory): String = category.name

    @TypeConverter
    fun toCategory(name: String): DrinkCategory = DrinkCategory.valueOf(name)
}