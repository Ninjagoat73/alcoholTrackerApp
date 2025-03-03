package com.example.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.alcoholtracker.di.DateConverter
import java.time.LocalDate

@Entity(tableName = "user_drink_logs")

data class UserDrinkLog(

    @PrimaryKey(autoGenerate = true) val logId: Int = 0,
    val drinkId: Int,
    val userId: Int,
    val name: String,
    val cost: Double?,
    val alcoholPercentage: Double,
    val amount: Double,
    val category: String?,
    val recipient: String?,
    @TypeConverters(DateConverter::class)
    val date: LocalDate
)
