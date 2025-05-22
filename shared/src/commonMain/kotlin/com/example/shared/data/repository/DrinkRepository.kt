package com.example.shared.data.repository


import com.example.shared.data.local.dao.DrinkDao
import com.example.shared.data.model.Drink
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkRepository @Inject constructor(private val drinkDao: DrinkDao) {
    fun getAllDrinks(): Flow<List<Drink>> = drinkDao.getAllDrinks()
}