package com.example.alcoholtracker.ui.components.logComponents.tabs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.alcoholtracker.data.model.UserDrinkLog

@Composable
fun FrequentList(drinks: List<UserDrinkLog>) {

    if (drinks.isEmpty()) {
        Text("No frequent drinks found")
    } else {
        LazyColumn() {
            items(drinks.size) { index ->
                val drink = drinks[index]
                Text("${drink.name} - ${drink.alcoholPercentage}%")
            }
        }
    }
}