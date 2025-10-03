package com.example.alcoholtracker.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.alcoholtracker.domain.model.DrinkCategory


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CategoryDropDown(
        selected: DrinkCategory?,
        onSelected: (DrinkCategory) -> Unit
    ) {


        var expanded by remember { mutableStateOf(false) }
        var search by remember { mutableStateOf("") }

        val categories = DrinkCategory.entries.toList()

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ){
            TextField(
                value = search,
                onValueChange = {search = it},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                    .clickable { expanded = true }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.nameString)},
                        onClick = {
                            onSelected(option)
                            expanded = false
                            search = option.nameString
                        }
                    )
                }
            }
        }
    }
