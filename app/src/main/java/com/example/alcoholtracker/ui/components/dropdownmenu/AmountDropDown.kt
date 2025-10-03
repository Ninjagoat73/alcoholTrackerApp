package com.example.alcoholtracker.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.alcoholtracker.R
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.domain.model.DrinkUnit
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountDropDown(
    selected: DrinkCategory?,
    onSelected: (DrinkUnit) -> Unit,
    onTyped : (String) -> Unit,
    onError: (Int) -> Unit,
    viewModel: DrinkViewModel,
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp))
    {

        var amount by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var search by remember { mutableStateOf("") }

        val options = try{
            viewModel.getDrinkUnitsForCategory(selected ?: DrinkCategory.OTHER)
        }
        catch (e: NotImplementedError){
            onError(R.string.feature_not_implemented)
            emptyList()
        }

        Card(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .height(64.dp)
                .weight(0.5f),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            TextField(
                value = amount,
                onValueChange = { text ->
                    amount = text.filter { txt -> txt.isDigit() }
                    onTyped(amount)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword
                ),


            )
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)
                .height(64.dp)
        ){
            TextField(
                value = search,
                onValueChange = {search = it},
                readOnly = true,
                label = { Text("Serving Size") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                    .clickable { expanded = true }
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
                            expanded = false
                            search = option.name
                        }
                    )
                }
            }
        }
    }
}
