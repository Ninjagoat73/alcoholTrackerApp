package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.data.model.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val drinkDao: DrinkDao
) : ViewModel() {

    val drinks: StateFlow<List<Drink>> = drinkDao.getAllDrinks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addDrink(drink: Drink) {
        viewModelScope.launch {
            drinkDao.insertDrinks(listOf(drink)) // Uses insertDrinks function from DrinkDao
        }
    }
}