package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.alcoholtracker.data.model.UserDrinkLog
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDrinkLogDao {
    @Query("SELECT * FROM user_drink_logs WHERE userId = :userId ORDER BY date DESC")
    fun getUserLogs(userId: Int): Flow<List<UserDrinkLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(log: UserDrinkLog)

    @Delete
    suspend fun deleteDrinkLog(log: UserDrinkLog)
}