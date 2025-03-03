package com.example.alcoholtracker.data.repository


import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.data.model.Drink
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkRepository @Inject constructor(private val drinkDao: DrinkDao) {
    fun getAllDrinks(): Flow<List<Drink>> = drinkDao.getAllDrinks()
}