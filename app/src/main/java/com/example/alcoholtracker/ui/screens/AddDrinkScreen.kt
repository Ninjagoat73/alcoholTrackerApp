package com.example.alcoholtracker.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import com.example.alcoholtracker.domain.usecase.DrinkCreateRequest
import com.example.alcoholtracker.domain.usecase.adddrinkfuns.getFinalAmount
import com.example.alcoholtracker.domain.usecase.adddrinkfuns.getLocalDateTime
import com.example.alcoholtracker.ui.components.LogDrinkTopBar
import com.example.alcoholtracker.ui.components.logComponents.ABVAndPriceTextFields

import com.example.alcoholtracker.ui.components.logComponents.AmountDropDown
import com.example.alcoholtracker.ui.components.logComponents.CategoryDropDown

import com.example.alcoholtracker.ui.components.logComponents.DateAndTimePicker
import com.example.alcoholtracker.ui.components.logComponents.DrinkAutoComplete
import com.example.alcoholtracker.ui.components.logComponents.RecipientAutoComplete
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.vamsi.snapnotify.SnapNotify
import java.time.LocalDate
import java.time.LocalTime


@Composable
fun AddDrinkScreen(
    onAddDrink: () -> Unit,
    viewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
    drinkViewModel: DrinkViewModel = hiltViewModel()
) {

    var alcoholPercentage by remember { mutableDoubleStateOf(0.0) }
    var cost by remember { mutableDoubleStateOf(0.0) }
    var selectedCategory by remember { mutableStateOf<DrinkCategory?>(null) }
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var selectedDrinkUnit by remember { mutableStateOf<DrinkUnit?>(DrinkUnit("milliliters", 1)) }
    var selectedAmount by remember { mutableIntStateOf(500) }
    var typedDrinkName by remember { mutableStateOf("") }
    var recipient by remember { mutableStateOf("Me") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val scrollState = rememberScrollState()




    Scaffold(
        topBar = { LogDrinkTopBar() },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val request = DrinkCreateRequest(
                        name = selectedDrink?.name ?: typedDrinkName,
                        category = selectedCategory ?: DrinkCategory.OTHER,
                        abv = selectedDrink?.alcoholContent ?: alcoholPercentage,
                        volume = getFinalAmount(selectedDrinkUnit, selectedAmount.toDouble()),
                        cost = cost,
                        recipient = recipient,
                        dateTime = getLocalDateTime(selectedDate, selectedTime),
                        logId = null
                    )
                    viewModel.logDrink(request)
                    onAddDrink()
                },
                icon = { Icon(Icons.Filled.Add, "Add Button") },
                text = { Text("Add Drink") }

            )
        }


    ) { innerPadding ->


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        )
        {
            CategoryDropDown(
                selected = selectedCategory,
                onSelected = {
                    tryOrNotify() {
                        selectedCategory = it
                    }
                },
            )
            DrinkAutoComplete(
                category = selectedCategory,
                onTyped = { typedDrinkName = it },
                onSelected = { selectedDrink = it },
            )

            AmountDropDown(
                drinkCategory = selectedCategory ?: DrinkCategory.OTHER,
                onSelected = { selectedDrinkUnit = it },
                onTyped = { selectedAmount = it },
            )

            ABVAndPriceTextFields(
                defaultABV = selectedDrink?.alcoholContent ?: 0.0,
                onABVChange = { alcoholPercentage = it },
                onPriceChange = { cost = it }
            )


            RecipientAutoComplete(
                onAction = { recipient = it },
            )

            DateAndTimePicker(
                currentDate = selectedDate,
                currentTime = selectedTime,
                onTimeSelected = { selectedTime = it },
                onDateSelected = {
                    selectedDate = it
                },
            )
        }
    }
}

inline fun tryOrNotify(block: () -> Unit) {
    try {
        block()
    } catch (e: NotImplementedError) {
        SnapNotify.showError("Feature not implemented")
    }
}
