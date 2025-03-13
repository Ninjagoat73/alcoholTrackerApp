package com.example.alcoholtracker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlin.math.exp

@Composable
fun AutoCompleteTextField(viewModel: DrinkViewModel){

    val categories = listOf(
        "Food",
        "Beverages",
        "Sports",
        "Learning",
        "Travel",
        "Rent",
        "Bills",
        "Fees",
        "Others",
    )

    val suggestions by viewModel.suggestions.collectAsState(initial = emptyList())

    var name by remember {
        mutableStateOf("")
    }

    val heightTextField by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember{
        MutableInteractionSource()
    }

    Column (
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable (
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )

    ){
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = "Drink name",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Column (modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextField)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinate ->
                            textFieldSize = coordinate.size.toSize()
                        },
                    value = name,
                    onValueChange = {
                        name = it
                        expanded = true
                        viewModel.fetchDrinkNames(it)
                    },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {expanded = !expanded}) {
                            Icon(
                                imageVector =  Icons.Rounded.ArrowDropDown,
                                contentDescription = "arrow"
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),

                    shape = RoundedCornerShape(10.dp)
                ) {
                    LazyColumn (
                        modifier = Modifier.heightIn(max = 150.dp)
                    ) {
                        if (name.isNotEmpty()){
                            items(
                                  suggestions.filter {
                                      it.lowercase()
                                          .contains(name.lowercase()) || it.lowercase()
                                          .contains("others")
                                  }.sorted()

                            ){
                                CategoryItems(title = it ){ title ->
                                    name = title
                                    expanded = false
                                }
                            }
                        } else{
                            items(
                                suggestions.sorted()
                            ){
                                CategoryItems(title = it) { title ->
                                    name = title
                                    expanded = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ){
        Text(text = title)
    }
}