package com.example.alcoholtracker.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.preferences.UserPreferences
import com.example.alcoholtracker.ui.components.progressbar.ProgressBarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    val currentType = when (currentTypeAsString) {
        "MONEY" -> ProgressBarType.MONEY
        "COUNT" -> ProgressBarType.COUNT
        "AMOUNT" -> ProgressBarType.AMOUNT
        else -> ProgressBarType.MONEY
    }

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