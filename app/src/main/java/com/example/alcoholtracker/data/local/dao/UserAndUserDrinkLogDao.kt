package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alcoholtracker.data.model.User
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import kotlinx.coroutines.flow.Flow
import java.nio.DoubleBuffer

@Dao
interface UserAndUserDrinkLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(log: UserDrinkLog)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User

    @Query("SELECT * FROM log WHERE userId = :userId")
    fun getDrinkLogsByUserId(userId: Int): Flow<List<UserDrinkLog>>

    @Query("""
            SELECT *
            FROM log 
            WHERE userId = :userId 
            AND date = date('now') 
            OR date = date('now', '-1 day')
            """)
    suspend fun getTwoDayLogs(userId: Int): List<UserDrinkLog>

}