package com.example.shared.data.repository

import com.example.shared.data.local.dao.UserAndUserDrinkLogDao
import com.example.shared.data.model.User
import com.example.shared.data.model.UserDrinkLog
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class UserAndUserDrinkLogRepository @Inject constructor(private val userAndUserDrinkLogDao: UserAndUserDrinkLogDao)  {

    suspend fun insertUser(user: User) {
        userAndUserDrinkLogDao.insertUser(user)
    }

    suspend fun insertDrinkLog(log: UserDrinkLog) {
        userAndUserDrinkLogDao.insertDrinkLog(log)
    }

    suspend fun deleteDrinkLog(log: UserDrinkLog){
        userAndUserDrinkLogDao.deleteDrinkLog(log)
    }

    suspend fun getUserById(userId: String): User {
        return userAndUserDrinkLogDao.getUserById(userId)
    }

    fun getDrinkLogsByUserId(userId: String): Flow<List<UserDrinkLog>> {
        return userAndUserDrinkLogDao.getDrinkLogsByUserId(userId)
    }

    suspend fun getTwoDayLogs(userId: String): List<UserDrinkLog>{
        return  userAndUserDrinkLogDao.getTwoDayLogs(userId)
    }
}