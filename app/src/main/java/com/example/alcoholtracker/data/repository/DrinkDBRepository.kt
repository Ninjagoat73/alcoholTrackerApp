package com.example.alcoholtracker.data.repository


import com.example.alcoholtracker.data.local.DrinkDBDao
import com.example.alcoholtracker.data.model.DrinkDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkDBRepository @Inject constructor(private val drinkDao: DrinkDBDao) {
    fun getAllDrinks(): Flow<List<DrinkDB>> = drinkDao.getAllDrinks()
}