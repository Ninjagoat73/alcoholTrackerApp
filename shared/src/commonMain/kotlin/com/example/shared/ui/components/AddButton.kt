package com.example.shared.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import com.example.shared.ui.navigation.Screen


@Composable
fun AddButton(navController: NavController){
    FloatingActionButton(
        onClick = { navController.navigate(Screen.AddDrink.rout)},
        )
    {
        Icon(Icons.Filled.Add, "Add Button")
    }
}