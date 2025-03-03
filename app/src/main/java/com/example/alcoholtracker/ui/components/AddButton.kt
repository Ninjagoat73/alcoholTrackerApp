package com.example.alcoholtracker.ui.components


import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


@Composable
fun AddButton(navController: NavController){
    FloatingActionButton(onClick = { navController.navigate("logDrink")}) {
        Icon(Icons.Filled.Add, "Add Button")
    }
}