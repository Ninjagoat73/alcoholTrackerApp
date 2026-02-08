package com.example.alcoholtracker.ui.components.logComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.round


@Composable
fun ABVAndPriceTextFields(
    defaultABV: Double,
    onABVChange: (Double) -> Unit,
    onPriceChange: (Double) -> Unit
) {


    var alcPercentage by remember { mutableDoubleStateOf(defaultABV) }
    var cost by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(defaultABV) {
        alcPercentage = defaultABV
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = "ABV",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, top = 16.dp)
            )
            OutlinedTextField(
                value = "$alcPercentage",
                onValueChange = { newValue ->
                    val filtered = newValue.filter { it.isDigit() || it == '.' }
                    val parsed = filtered.toDoubleOrNull()
                    if (parsed != null && parsed in 0.0..100.0 && filtered.length <= 5) {
                        alcPercentage = parsed
                        onABVChange(alcPercentage)
                    } else if (filtered.isEmpty()) {
                        alcPercentage = 0.0
                        onABVChange(0.0)
                    }
                },
                suffix = { Text("%") },
                trailingIcon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                val current = alcPercentage + 0.1
                                alcPercentage = round(current * 10.0) / 10.0
                                onABVChange(alcPercentage)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
                        }
                        IconButton(
                            onClick = {

                                if (alcPercentage > 0.0) {
                                    val current = alcPercentage - 0.1
                                    alcPercentage = round(current * 10.0) / 10.0
                                    onABVChange(alcPercentage)
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(start = 8.dp)
        ) {
            Text(
                text = "Price",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, top = 16.dp)
            )
            OutlinedTextField(
                value = "$cost",
                onValueChange = { newValue ->
                    val filtered = newValue.filter { it.isDigit() || it == '.' }
                    val parsed = filtered.toDoubleOrNull()
                    if (parsed != null && parsed > 0) {
                        cost = parsed
                        onPriceChange(cost)
                    } else if (filtered.isEmpty()) {
                        cost = 0.0
                        onPriceChange(0.0)
                    }
                },
                suffix = { Text("€") },
                trailingIcon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                val current = cost + 0.1
                                cost = round(current * 10.0) / 10.0
                                onPriceChange(cost)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Increase")
                        }
                        IconButton(
                            onClick = {

                                if (cost > 0.0) {
                                    val current = cost - 0.1
                                    cost = round(current * 10.0) / 10.0
                                    onPriceChange(cost)
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
    }
}


