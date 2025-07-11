package com.example.alcoholtracker.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.data.model.DrinkCategory
import com.example.alcoholtracker.domain.usecase.DrinkCreateRequest

import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun AddDrinkScreen(
    navController: NavController,
    viewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
    drinkViewModel: DrinkViewModel = hiltViewModel()) {
    var drinkName by remember { mutableStateOf("") }
    var alcoholPercentage by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Log a New Drink", style = MaterialTheme.typography.headlineSmall)

        TextField(value = drinkName, onValueChange = {drinkName = it}, label = { Text("Drink Name")})
        TextField(value = alcoholPercentage, onValueChange = { alcoholPercentage = it }, label = { Text("Alcohol %") })
        TextField(value = volume, onValueChange = { volume = it }, label = { Text("Volume (ml)") })
        TextField(value = cost, onValueChange = { cost = it }, label = { Text("Cost ($)") })

        Button(
            onClick = {
                val request = DrinkCreateRequest(
                    name = drinkName,
                    category = DrinkCategory.BEER,
                    abv = alcoholPercentage.toDoubleOrNull(),
                    volume = volume.toDoubleOrNull(),
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