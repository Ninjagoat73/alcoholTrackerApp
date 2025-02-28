package com.example.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_drinks")
data class UserDrink(
    @PrimaryKey val logID: Int,
    val drinkID: Int,
    val name: String,
    val cost: Double,
    val alcoholContent: Double,
    val category: String,
    val recipient: String,
)
