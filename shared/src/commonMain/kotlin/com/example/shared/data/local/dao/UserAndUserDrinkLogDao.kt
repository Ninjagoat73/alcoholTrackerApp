package com.example.shared.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shared.data.model.User
import com.example.shared.data.model.UserDrinkLog
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAndUserDrinkLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(log: UserDrinkLog)

    @Delete
    suspend fun deleteDrinkLog(log: UserDrinkLog)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): User

    @Query("SELECT * FROM log WHERE userId = :userId")
    fun getDrinkLogsByUserId(userId: String): Flow<List<UserDrinkLog>>

    @Query("""
            SELECT *
            FROM log 
            WHERE userId = :userId 
            AND date = date('now') 
            OR date = date('now', '-1 day')
            """)
    suspend fun getTwoDayLogs(userId: String): List<UserDrinkLog>

}