package com.example.alcoholtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.alcoholtracker.di.DateConverter
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "log")
data class UserDrinkLog(
    @ColumnInfo("logId")
    @PrimaryKey(autoGenerate = true) val logId: Int = 0,

    @ColumnInfo("drinkId")
    val drinkId: Int?,

    @ColumnInfo("userId")
    val userId: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("cost")
    val cost: Double?,

    @ColumnInfo("alcoholPercentage")
    val alcoholPercentage: Double?,

    @ColumnInfo("amount")
    val amount: Double?,

    @ColumnInfo("category")
    val category: DrinkCategory,

    @ColumnInfo("recipient")
    val recipient: String?,

    @TypeConverters(DateConverter::class)
    @ColumnInfo("date")
    val date: LocalDateTime

)
