package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.local.dao.UserDrinkLogDao
import com.example.alcoholtracker.data.model.UserDrinkLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class UserDrinkLogViewModel @Inject constructor(
    private val userDrinkLogDao: UserDrinkLogDao
) : ViewModel() {

    private val mockUserDrinks = listOf(
        UserDrinkLog(
            logId = 1,
            userId = 123,
            drinkId = 1,  // Heineken from `drinks_db`
            name = "Heineken",
            alcoholPercentage = 5.0,
            amount = 330.0,
            date = LocalDate.of(2024, Month.JUNE, 25),  // Yesterday
            cost = 5.0,
            recipient = "Self",
            category = "Beer"
        ),
        UserDrinkLog(
            logId = 2,
            userId = 123,
            drinkId = 2,  // Heineken from `drinks_db`
            name = "Jack Daniels",
            alcoholPercentage = 40.0,
            amount = 330.0,
            date = LocalDate.now(),
            cost = 5.0,
            recipient = "Self",
            category = "Whiskey"
        )
    )

    private val _userId = MutableStateFlow(123)

    val userDrinkLogs = _userId.flatMapLatest { id ->
        userDrinkLogDao.getUserLogs(id)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val dailySpending: StateFlow<Double> = userDrinkLogs.map { logs ->
        calculateSpending(logs,1)
    }.stateIn(viewModelScope, SharingStarted.Lazily,0.0)

    fun logDrink(drink: UserDrinkLog) {
        viewModelScope.launch {
            userDrinkLogDao.insertDrinkLog(drink)
        }
    }

    private fun calculateSpending(drinks: List<UserDrinkLog>, daysAgo: Int): Double{
        val today = System.currentTimeMillis()
        val startOfDay = today - (daysAgo *24*60*60*1000)

        return drinks
            .filter { it.date >= LocalDate.now() }
            .sumOf { it.cost ?: 0.0 }
    }

    init {
        viewModelScope.launch {
            userDrinkLogDao.insertDrinkLog(mockUserDrinks[0])
            userDrinkLogDao.insertDrinkLog(mockUserDrinks[1])
        }
    }
}
