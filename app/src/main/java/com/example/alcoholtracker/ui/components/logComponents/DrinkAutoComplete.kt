package com.example.alcoholtracker.ui.components.logComponents

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcoholtracker.data.model.Drink
import com.example.alcoholtracker.domain.model.DrinkCategory
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkAutoComplete(
    category: DrinkCategory?,
    onTyped : (String) -> Unit,
    onSelected: (Drink) -> Unit,
    drinkViewModel: DrinkViewModel
){
    val uiState by drinkViewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    val options = uiState.suggestions


    LaunchedEffect(category) {
        selected = ""
    }

    LaunchedEffect(selected, category) {
        if (category == null) return@LaunchedEffect

        snapshotFlow { selected }
            .debounce(300)
            .distinctUntilChanged()
            .collect { query ->
                drinkViewModel.searchDrinks(query, category)
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Text(
            text = "Drink Name",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp, top = 16.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {selected = it
                                onTyped(selected)},
                label = null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                    .clickable { expanded = true }
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                placeholder = {Text("Select a drink...")}
            )

            val filteringOptions =
                options.filter { it.name.contains(selected, ignoreCase = true) }
            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selected = selectionOption.name
                                expanded = false
                                onSelected(selectionOption)
                            },
                            text =   {Text(text = selectionOption.name)}
                        )
                    }
                }
            }
        }
    }


}
