package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Update
    suspend fun updateDrinkLog(log: UserDrinkLog)

    @Delete
    suspend fun deleteDrinkLog(log: UserDrinkLog)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): User

    @Query("SELECT * FROM log WHERE userId = :userId")
    fun getDrinkLogsByUserId(userId: String): Flow<List<UserDrinkLog>>

    @Query("""
    SELECT * FROM log 
    WHERE userId = :userId 
    AND date(date) IN (date('now'), date('now', '-1 day'))
""")
    fun getTwoDayLogs(userId: String): Flow<List<UserDrinkLog>>

}