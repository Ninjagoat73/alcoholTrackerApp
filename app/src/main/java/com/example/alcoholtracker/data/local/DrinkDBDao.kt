package com.example.alcoholtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alcoholtracker.data.model.DrinkDB
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDBDao {

    @Query("SELECT * FROM drinks_db ORDER BY name ASC")
    fun getAllDrinks(): Flow<List<DrinkDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: DrinkDB)
}