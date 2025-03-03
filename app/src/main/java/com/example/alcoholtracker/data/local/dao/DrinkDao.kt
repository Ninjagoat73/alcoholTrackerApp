package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alcoholtracker.data.model.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drinks_db ORDER BY name ASC")
    fun getAllDrinks(): Flow<List<Drink>>

    @Query("SELECT name FROM drinks_db WHERE name LIKE :query || '%'")
    suspend fun getDrinkNames(query: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks(drink: List<Drink>)

    @Insert
    suspend fun insertDrink(drink: Drink)
}