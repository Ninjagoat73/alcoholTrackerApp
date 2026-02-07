package com.example.alcoholtracker.ui.components.logComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountDropDown(
    drinkCategory: DrinkCategory?,
    onSelected: (DrinkUnit) -> Unit,
    onTyped : (Int) -> Unit,
    viewModel: DrinkViewModel,
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp))
    {
        var selected by remember { mutableStateOf<DrinkUnit?>(DrinkUnit("milliliters", 1)) }
        var amount by remember { mutableIntStateOf(1) }
        var expanded by remember { mutableStateOf(false) }
        val options = viewModel.getDrinkUnitsForCategory(drinkCategory ?: DrinkCategory.OTHER)


        Column(){
            Row() {
                Text(
                text = "Amount",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 8.dp, top = 12.dp, end = 8.dp)
                    .weight(0.5F)
                )
                Text(
                text = "Unit",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 16.dp, top = 12.dp)
                    .weight(1F)
                )
            }
            Row() {

                OutlinedTextField(
                    value = amount.toString(),
                    onValueChange = {
                        amount = it.toInt()
                        onTyped(amount)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.weight(0.5f).padding(end = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    trailingIcon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                val current = amount
                                amount = current + 1
                                onTyped(amount)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
                        }
                        IconButton(
                            onClick = {
                                val current = amount
                                if (current > 0) {
                                    amount = (current - 1)
                                    onTyped(amount)
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                        }
                    }
                },
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f)
                        .height(64.dp)
                ){
                    OutlinedTextField(
                        value = selected?.name ?: "Select a unit",
                        onValueChange = { },
                        readOnly = true,
                        label = null,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                            .clickable {
                                expanded = true }
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(24.dp)

                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.name) },
                                onClick = {
                                    onSelected(option)
                                    selected = option
                                    expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
        }
    }
}
