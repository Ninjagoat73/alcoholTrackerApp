package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alcoholtracker.data.model.Drink
import kotlinx.coroutines.flow.Flow

@Dao

interface DrinkDao {

    @Query("SELECT * FROM drinks_db ")
    suspend fun getAllDrinks(): List<Drink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks(drink: List<Drink>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: Drink)
}