package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.alcoholtracker.ui.components.progressbar.ProgressBarType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressBarViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val currentTypeAsString: String = userPreferences.progressBarType
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "MONEY").toString()


    val currentType: StateFlow<ProgressBarType> = userPreferences.progressBarType
        .map { typeString -> when(typeString) {
            "MONEY" -> ProgressBarType.MONEY
            "COUNT" -> ProgressBarType.COUNT
            "AMOUNT" -> ProgressBarType.AMOUNT
            else -> ProgressBarType.MONEY
        }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressBarType.MONEY)

    val currentTarget: StateFlow<Double> = userPreferences.progressBarTarget.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0)

    fun updateTarget(value: Double) = viewModelScope.launch {
        userPreferences.updateTarget(value)
    }

    fun updateType(type: String) = viewModelScope.launch {
        userPreferences.updateType(type)
    }



}