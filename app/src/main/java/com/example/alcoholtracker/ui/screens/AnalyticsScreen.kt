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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.AnalyticsTopBar
import com.example.alcoholtracker.ui.components.HomeTopBar
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
        }

    }
}