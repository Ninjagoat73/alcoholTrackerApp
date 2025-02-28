package com.example.alcoholtracker.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.alcoholtracker.ui.theme.AlcoholTrackerTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.alcoholtracker.utils.getFormattedDate

fun homeScreen(){

}


@Composable
fun DailySpending(dailySpending: Double){
    Text("Current Daily Spending")
}

fun goalBar(){

}

fun dailyDrinkList(){

}

@Composable
fun CurrentDayText(){
    val todayDate = getFormattedDate(LocalDate.now())
    val tomorrowDate = getFormattedDate(LocalDate.now().plusDays(1))
    Text("$todayDate - $tomorrowDate")
}

@Preview (showBackground = true)
@Composable
fun PreviewFunction(){
    AlcoholTrackerTheme {
        CurrentDayText()
    }
}