package com.example.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks_db")
data class Drink(
    @PrimaryKey(autoGenerate = true) val drinkId: Int = 0,
    val name: String,
    val alcoholContent: Double,
    val category: String,
    )
