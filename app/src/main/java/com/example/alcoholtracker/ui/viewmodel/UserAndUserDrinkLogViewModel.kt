package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UserAndUserDrinkLogViewModel @Inject constructor(
    private val userAndUserDrinkLogRepository: UserAndUserDrinkLogRepository
) : ViewModel() {


    private val _userId = MutableStateFlow(123)
    private val _twoDaySummary = MutableStateFlow<List<UserDrinkLog>?>(null)
    private val _drinkLogs = MutableStateFlow<List<UserDrinkLog>>(emptyList())
    val twoDaySummary: StateFlow<List<UserDrinkLog>?> = _twoDaySummary

    val drinkLogs: StateFlow<List<UserDrinkLog>> = userAndUserDrinkLogRepository
        .getDrinkLogsByUserId(_userId.value)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    fun getTwoDaySummary() {
        viewModelScope.launch {
            val summary = userAndUserDrinkLogRepository.getTwoDayLogs(_userId.value)
            _twoDaySummary.value = userAndUserDrinkLogRepository.getTwoDayLogs(_userId.value)
        }
    }


    fun logDrink(drinkLog: UserDrinkLog){
        viewModelScope.launch {
            userAndUserDrinkLogRepository.insertDrinkLog(drinkLog)
        }
    }

    init { viewModelScope.launch {
        getTwoDaySummary()
    } }
}
