package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
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
class UserAndUserDrinkLogViewModel @Inject constructor(
    private val userAndUserDrinkLogRepository: UserAndUserDrinkLogRepository
) : ViewModel() {


    private val _userId = MutableStateFlow(123)
    private val _totalSpent = MutableLiveData<Double>()
    val totalSpent: LiveData<Double> = _totalSpent

    fun fetchTotalSpent(date: LocalDate) {
        viewModelScope.launch {
            _totalSpent.value = UserAndUserDrinkLogRepository.getDailyCost(_userId.value, date)
        }
    }

    fun logDrink(drink: UserDrinkLog) {
        viewModelScope.launch {
            userDrinkLogRepository.insertDrinkLog(drink)
        }
    }

    private fun calculateSpending(drinks: List<UserDrinkLog>, daysAgo: Int): Double{
        val today = System.currentTimeMillis()
        val startOfDay = today - (daysAgo *24*60*60*1000)

        return drinks
            .filter { it.date >= LocalDate.now() }
            .sumOf { it.cost ?: 0.0 }
    }

    init { viewModelScope.launch {} }
}
