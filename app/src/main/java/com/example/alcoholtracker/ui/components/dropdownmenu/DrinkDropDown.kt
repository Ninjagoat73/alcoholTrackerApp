package com.example.alcoholtracker.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.room.util.query
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDropDown(
    selected : DrinkCategory,
    viewModel: DrinkViewModel,
    onSelected: (Drink) -> Unit) {
    val itemList = viewModel.getDrinksForCategory(selected)

    var expanded by remember { mutableStateOf(false) }
    var selectedDrink by remember { mutableStateOf<Drink?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredDrinks = remember(searchQuery, itemList) {
        if (searchQuery.isBlank()) itemList
        else itemList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ){
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                expanded = true
            },
            label = { Text("Drink Name") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredDrinks.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name)},
                    onClick = {
                        onSelected(option)
                        searchQuery = option.name
                        expanded = false
                        selectedDrink = option
                    }
                )
            }
        }
    }

}

