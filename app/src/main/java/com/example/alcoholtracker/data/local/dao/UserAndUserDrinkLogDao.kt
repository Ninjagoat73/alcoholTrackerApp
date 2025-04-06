package com.example.alcoholtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alcoholtracker.data.model.User
import com.example.alcoholtracker.data.model.UserDrinkLog

@Dao
interface UserAndUserDrinkLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertDrinkLog(log: UserDrinkLog)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User

    @Query("SELECT * FROM log WHERE userId = :userId")
    suspend fun getDrinkLogsByUserId(userId: Int): List<UserDrinkLog>

    @Query("Select * FROM users WHERE userEmail = :userEmail AND userPassword = :userPassword")
    fun verifyUserLogin(userEmail: String, userPassword: String)

}