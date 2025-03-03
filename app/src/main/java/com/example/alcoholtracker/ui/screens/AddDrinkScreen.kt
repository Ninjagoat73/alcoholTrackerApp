package com.example.alcoholtracker.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.ui.viewmodel.UserDrinkLogViewModel
import java.time.LocalDate



@Composable
fun AddDrinkScreen(navController: NavController, viewModel: UserDrinkLogViewModel = hiltViewModel()) {
    var drinkName by remember { mutableStateOf("") }
    var alcoholPercentage by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Log a New Drink", style = MaterialTheme.typography.headlineSmall)

        TextField(value = drinkName, onValueChange = { drinkName = it }, label = { Text("Drink Name") })
        TextField(value = alcoholPercentage, onValueChange = { alcoholPercentage = it }, label = { Text("Alcohol %") })
        TextField(value = volume, onValueChange = { volume = it }, label = { Text("Volume (ml)") })
        TextField(value = cost, onValueChange = { cost = it }, label = { Text("Cost ($)") })

        Button(
            onClick = {
                viewModel.logDrink(
                    UserDrinkLog(
                        drinkId = 2,
                        userId = 123,
                        name = drinkName,
                        alcoholPercentage = alcoholPercentage.toDoubleOrNull() ?: 0.0,
                        amount = volume.toDoubleOrNull() ?: 0.0,
                        date = LocalDate.now(),
                        cost = cost.toDoubleOrNull() ?: 0.0,
                        category = "Beer",
                        recipient = "Self",

                    )
                )
                navController.popBackStack()  // Navigate back to HomeScreen
            }
        ) {
            Text("Save Drink")
        }
    }
}