package com.example.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks_db")
data class Drink(
    @PrimaryKey val drinkId: Int,
    val name: String,
    val alcoholContent: Double,
    val category: String,
    val brand: String?
    )
