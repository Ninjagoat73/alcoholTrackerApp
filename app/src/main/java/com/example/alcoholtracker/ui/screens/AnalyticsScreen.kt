package com.example.alcoholtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.data.analytics.PieSlice
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.AnalyticsTopBar
import com.example.alcoholtracker.ui.components.HomeTopBar
import com.example.alcoholtracker.ui.components.analytics.AnalyticsCard
import com.example.alcoholtracker.ui.components.analytics.AnalyticsState
import com.example.alcoholtracker.ui.components.analytics.CardStyle
import com.example.alcoholtracker.ui.components.analytics.graphs.PieChart
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

@Composable
fun AnalyticsScreen(
    navController: NavController,
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
){

    var selectedCard by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {

            Text("This is the analytics page")

            val data = listOf(
                PieSlice(
                    color = Color.Blue,
                    label = "Blue",
                    value = 2.1,
                    isTapped = false,
                ),
                PieSlice(
                    color = Color.Red,
                    label = "Red",
                    value = 4.10,
                    isTapped = false,
                ),
                PieSlice(
                    color = Color.Green,
                    label = "Green",
                    value = 1.1,
                    isTapped = false,
                )
            )
            val sortedData = data.sortedByDescending { it.value }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().heightIn(max = 1000.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(4) { index ->
                    var state by remember { mutableStateOf(AnalyticsState.NONE) }

                    AnalyticsCard(

                        state = state,
                        data = sortedData,
                        title = "Card $index",
                        style = CardStyle.GridSquare,
                        onClicked = {
                            selectedCard = index
                            state = AnalyticsState.DETAILS
                        }
                    ) {
                        PieChart(
                            sortedData,
                            modifier = it)
                    }
                }
            }
            AnalyticsCard(
                state = AnalyticsState.NONE,
                data = sortedData,
                title = "landscape",
                style = CardStyle.Landscape,
                onClicked = {}
            ) {
                Text("sup")
            }
            if (selectedCard != null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))

                )

                AnalyticsCard(
                    state = AnalyticsState.DETAILS,
                    title = "Card $selectedCard",
                    style = CardStyle.GridSquare,
                    data = data,
                    onClicked = {  }
                ) {  modifier ->
                    PieChart(data, modifier = modifier)
                }
            }

        }

    }
}