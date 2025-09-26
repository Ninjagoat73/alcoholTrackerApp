package com.example.alcoholtracker.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.R
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import com.example.alcoholtracker.domain.usecase.DrinkCreateRequest
import com.example.alcoholtracker.ui.components.dropdownmenu.AmountDropDown
import com.example.alcoholtracker.ui.components.dropdownmenu.CategoryDropDown
import com.example.alcoholtracker.ui.components.dropdownmenu.DrinkAutoComplete



import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.vamsi.snapnotify.SnapNotify


@Composable
fun AddDrinkScreen(
    navController: NavController,
    viewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
    drinkViewModel: DrinkViewModel = hiltViewModel()) {

    var drinkName by remember { mutableStateOf("") }
    var alcoholPercentage by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<DrinkCategory?>(null) }
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var selectedDrinkUnit by remember { mutableStateOf<DrinkUnit?>(null) }
    var selectedAmount by remember { mutableStateOf("") }
    var typedDrinkName by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp)) {
        Text("Log a New Drink", style = MaterialTheme.typography.headlineSmall)

        CategoryDropDown(
            selected = selectedCategory,
            onSelected = {
                tryOrNotify() {
                    selectedCategory = it
                }
            }
        )

        AmountDropDown(
            selected = selectedCategory ?: DrinkCategory.OTHER,
            onSelected = {
                tryOrNotify(){
                    selectedDrinkUnit = it
                } },
            onTyped = {
                tryOrNotify(){
                    selectedAmount = it
                } },
            viewModel = drinkViewModel,
            onError = {

            }
        )


        DrinkAutoComplete(
            category = selectedCategory ?: DrinkCategory.OTHER,
            drinkViewModel = drinkViewModel,
            onTyped = { tryOrNotify() {
                typedDrinkName = it
            } },
            onSelected = { tryOrNotify() {
                selectedDrink = it
            } },
            onError = {

            }
        )




        TextField(value = alcoholPercentage, onValueChange = { alcoholPercentage = it }, label = { Text("Alcohol %") })

        TextField(value = cost, onValueChange = { cost = it }, label = { Text("Cost ($)") })

        Button(
            onClick = {
                val request = DrinkCreateRequest(
                    name = selectedDrink?.name ?: typedDrinkName,
                    category = selectedCategory ?: DrinkCategory.OTHER,
                    abv = selectedDrink?.alcoholContent ?: alcoholPercentage.toDoubleOrNull(),
                    volume = getFinalAmount(selectedDrinkUnit, selectedAmount.toDoubleOrNull()),
                    cost = cost.toDoubleOrNull(),
                    recipient = "Self",
                    dateTime = null,
                    logId = null)
                viewModel.logDrink(request)
                navController.popBackStack()
            }
        ) {
            Text("Save Drink")
        }
    }
}

fun getFinalAmount(unit: DrinkUnit?, amount: Double?) : Double{
    if ( unit != null && amount != null) {
        return unit.amount * amount
    }
    else return 0.0
}

inline fun tryOrNotify(block: () -> Unit) {
    try {
        block()
    } catch (e: NotImplementedError) {
        SnapNotify.showError("Feature not implemented")
    }
}