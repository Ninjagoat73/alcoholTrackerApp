package com.example.alcoholtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.AlcoholListHome
import com.example.alcoholtracker.ui.components.HomeTopBar
import com.example.alcoholtracker.ui.components.progressbar.MoneyProgressBar


import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import java.time.LocalDate
import com.example.alcoholtracker.utils.getFormattedDate

@Composable
fun HomeScreen(
    navController: NavController,
    drinkViewModel: DrinkViewModel = hiltViewModel(),
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
){
    val drinks by drinkViewModel.drinks.collectAsState()
    val userDrinkLogs by userDrinkLogViewModel.drinkLogs.collectAsState()
    val twoDaySummary = userDrinkLogViewModel.twoDaySummary


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {HomeTopBar() {} },
        floatingActionButton = {AddButton(navController)},
        modifier = Modifier.fillMaxSize()


    ) {
        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {

            AlcoholListHome(userDrinkLogs)

            var progressBar = MoneyProgressBar()

            progressBar.ProgressBarCard(
                twoDaySummary.value?.totalCost ?: 0.0,
                twoDaySummary.value?.drinkCount?.toDouble() ?: 0.0,
                twoDaySummary.value?.totalAmount ?: 0.0,
                200.0)
        }



        /*
        Column(modifier = Modifier
            .padding(16.dp),
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text("Your Drink Logs:", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(userDrinkLogs) { log ->
                    Text("${log.name} - ${log.alcoholPercentage}% on ${log.date}")
                }
            }

            DailySpending(dailySpending)

            Spacer(modifier = Modifier.height(16.dp))


        }*/
    }
}


@Composable
fun DailySpending(dailySpending: Double){
    Text("Current Daily Spending: $dailySpending",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center)
}

fun goalBar(){

}

fun dailyDrinkList(){

}



@Preview (showBackground = true)
@Composable
fun PreviewFunction(){

}

