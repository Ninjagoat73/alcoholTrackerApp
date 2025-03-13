package com.example.alcoholtracker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.alcoholtracker.utils.getFormattedDate
import com.example.compose.AlcoholTrackerTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onCalendarClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrentDayText()
            }
        },
        actions = {
            IconButton(onClick = onCalendarClick) {
                Icon(Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    )
}

@Composable
fun CurrentDayText(){
    val todayDate = getFormattedDate(LocalDate.now())
    val yesterdayDate = getFormattedDate(LocalDate.now().plusDays(-1))
    Text("$yesterdayDate - $todayDate",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
@Preview
fun preview(){
    AlcoholTrackerTheme {
        HomeTopBar(){
            //TODO
        }
    }
}