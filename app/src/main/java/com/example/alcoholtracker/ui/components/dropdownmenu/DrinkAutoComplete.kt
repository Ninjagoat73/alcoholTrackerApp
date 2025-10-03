package com.example.alcoholtracker.ui.components.dropdownmenu

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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

/*
from: https://medium.com/@ramadan123sayed/building-a-custom-autocomplete-component-with-jetpack-compose-using-basictextfield-lazycolumn-c6d8008683aa
 */

@Composable
fun DrinkAutoComplete(
    category: DrinkCategory,
    drinkViewModel: DrinkViewModel,
    onSelected : (Drink) -> Unit,
    onTyped : (String) -> Unit,
    onError: (Int) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val suggestions = drinkViewModel.suggestions.collectAsState()



    var showSuggestions by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(textFieldValue.text) {
        snapshotFlow { textFieldValue.text }
            .debounce(300)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .collect { query ->
                drinkViewModel.searchDrinks(query, category)

            }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it
                                onTyped(it.text)
                                showSuggestions = true},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                cursorBrush = SolidColor(Color.Blue),
                decorationBox = { innerTextField ->
                    if (textFieldValue.text.isEmpty()) {
                        Text(
                            text = "Enter Drink...",
                            style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                        )
                    }
                    innerTextField()
                }
            )
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .animateContentSize()
        ) {
            if (showSuggestions && suggestions.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(
                        items = suggestions.value,
                        key = { suggestion -> suggestion.name }
                    ) { suggestion ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    textFieldValue = TextFieldValue(
                                        text = suggestion.name,
                                        selection = TextRange(suggestion.name.length)
                                    )
                                    onSelected(suggestion)
                                    showSuggestions = false
                                }
                                .padding(12.dp)
                        ) {
                            Text(
                                text = suggestion.name,
                                style = TextStyle(fontSize = 18.sp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            } else if (showSuggestions && suggestions.value.isEmpty()) {
                Text(
                    text = "No drinks available",
                    style = TextStyle(color = Color.Gray, fontSize = 16.sp),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
