package com.example.alcoholtracker.ui.components



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.alcoholtracker.ui.navigation.Screen


@Composable
fun AddButton(navController: NavController){
    FloatingActionButton(
        onClick = { navController.navigate(Screen.AddDrink.rout)},
        )
    {
        Icon(Icons.Filled.Add, "Add Button")
    }
}