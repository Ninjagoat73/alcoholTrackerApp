package com.example.alcoholtracker.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.alcoholtracker.ui.components.LogDrinkTopBar
import com.example.alcoholtracker.ui.components.logComponents.ABVTextField
import com.example.alcoholtracker.ui.components.logComponents.AmountDropDown
import com.example.alcoholtracker.ui.components.logComponents.CategoryDropDown
import com.example.alcoholtracker.ui.components.logComponents.CostTextField
import com.example.alcoholtracker.ui.components.logComponents.DrinkAutoComplete
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.vamsi.snapnotify.SnapNotify


@Composable
fun AddDrinkScreen(
    onAddDrink: () -> Unit,
    viewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
    drinkViewModel: DrinkViewModel = hiltViewModel()) {

    var alcoholPercentage by remember { mutableDoubleStateOf(0.0) }
    var cost by remember { mutableDoubleStateOf(0.0) }
    var selectedCategory by remember { mutableStateOf<DrinkCategory?>(null) }
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var selectedDrinkUnit by remember { mutableStateOf<DrinkUnit?>(DrinkUnit("milliliters", 1)) }
    var selectedAmount by remember { mutableIntStateOf(1) }
    var typedDrinkName by remember { mutableStateOf("") }


    Scaffold(
        topBar = { LogDrinkTopBar() },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()


    ) { innerPadding ->
         Column(
             horizontalAlignment = Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.Center,
             modifier = Modifier
                 .padding(top = innerPadding.calculateTopPadding())
                 .fillMaxWidth()
                 .padding(horizontal = 24.dp))
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
                onTyped = {typedDrinkName = it},
                onSelected = {selectedDrink = it},
                drinkViewModel = drinkViewModel
            )

            AmountDropDown(
                drinkCategory = selectedCategory ?: DrinkCategory.OTHER,
                onSelected = { selectedDrinkUnit = it },
                onTyped = { selectedAmount = it },
                viewModel = drinkViewModel,
            )

            ABVTextField(
                 defaultABV = selectedDrink?.alcoholContent ?: 0.0,
                 onValueChange = {alcoholPercentage = it}
             )

            CostTextField(
                onValueChange = {cost = it}
            )

            Button(
                onClick = {
                    val request = DrinkCreateRequest(
                        name = selectedDrink?.name ?: typedDrinkName,
                        category = selectedCategory ?: DrinkCategory.OTHER,
                        abv = selectedDrink?.alcoholContent ?: alcoholPercentage,
                        volume = getFinalAmount(selectedDrinkUnit, selectedAmount.toDouble()),
                        cost = cost,
                        recipient = "Self",
                        dateTime = null,
                        logId = null)
                    viewModel.logDrink(request)
                    onAddDrink()
                }
            ) {
                Text("Save Drink")
            }
        }
    }
}

fun getFinalAmount(unit: DrinkUnit?, amount: Double?) : Int{

    Log.d("DrinkUnit", "Unit: $unit, Amount: $amount")

    return if ( unit != null && amount != null) {
        (unit.amount * amount).toInt()
    }
    else 0
}

inline fun tryOrNotify(block: () -> Unit) {
    try {
        block()
    } catch (e: NotImplementedError) {
        SnapNotify.showError("Feature not implemented")
    }
}
