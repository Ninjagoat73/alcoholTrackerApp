package com.example.alcoholtracker.data.repository

import com.example.alcoholtracker.data.local.dao.UserAndUserDrinkLogDao
import com.example.alcoholtracker.data.model.User
import com.example.alcoholtracker.data.model.UserDrinkLog

import javax.inject.Inject

class UserAndUserDrinkLogRepository @Inject constructor(private val userAndUserDrinkLogDao: UserAndUserDrinkLogDao)  {

    suspend fun insertUser(user: User) {
        userAndUserDrinkLogDao.insertUser(user)
    }

    suspend fun insertDrinkLog(log: UserDrinkLog) {
        userAndUserDrinkLogDao.insertDrinkLog(log)
    }

    suspend fun getUserById(userId: Int): User {
        return userAndUserDrinkLogDao.getUserById(userId)
    }

    suspend fun getDrinkLogsByUserId(userId: Int): List<UserDrinkLog> {
        return userAndUserDrinkLogDao.getDrinkLogsByUserId(userId)
    }
}