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

        }
    }
}
