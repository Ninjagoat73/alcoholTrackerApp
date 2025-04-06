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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UserAndUserDrinkLogViewModel @Inject constructor(
    private val userAndUserDrinkLogRepository: UserAndUserDrinkLogRepository
) : ViewModel() {


    private val _userId = MutableStateFlow(123)
    private val _twoDaySummary = MutableLiveData<UserDrinkLogSummary>()
    private val _drinkLogs = MutableStateFlow<List<UserDrinkLog>>(emptyList())
    val drinkLogs: StateFlow<List<UserDrinkLog>> = _drinkLogs
    val twoDaySummary: LiveData<UserDrinkLogSummary> = _twoDaySummary



    fun getTwoDaySummary() {
        viewModelScope.launch {
            _twoDaySummary.value = userAndUserDrinkLogRepository.getTwoDaySummary(_userId.value)
        }
    }

    fun getDrinkLogsByUserId(){
        viewModelScope.launch {
            _drinkLogs.value = userAndUserDrinkLogRepository.getDrinkLogsByUserId(_userId.value)
        }
    }

    fun logDrink(drinkLog: UserDrinkLog){
        viewModelScope.launch {
            userAndUserDrinkLogRepository.insertDrinkLog(drinkLog)
        }
    }

    init { viewModelScope.launch {} }
}
