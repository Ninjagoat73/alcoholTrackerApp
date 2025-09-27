package com.example.alcoholtracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.data.analytics.PieSlice
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.AnalyticsTopBar
import com.example.alcoholtracker.ui.components.HomeTopBar
import com.example.alcoholtracker.ui.components.analytics.graphs.PieChart
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

@Composable
fun AnalyticsScreen(
    navController: NavController,
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Text("This is the analytics page")

            val data = listOf(
                PieSlice(
                    color = Color.Blue,
                    label = "Blue",
                    value = 2.1,
                    isTapped = false,
                ),
                PieSlice(
                    color = Color.Red,
                    label = "Red",
                    value = 4.10,
                    isTapped = false,
                ),
                PieSlice(
                    color = Color.Green,
                    label = "Green",
                    value = 1.1,
                    isTapped = false,
                )
            )
            val sortedData = data.sortedByDescending { it.value }

            PieChart(sortedData)
        }

    }
}