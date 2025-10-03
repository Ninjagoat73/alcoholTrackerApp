package com.example.alcoholtracker.ui.screens.analytics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.alcoholtracker.ui.components.AnalyticsTopBar
import com.example.alcoholtracker.ui.navigation.AnalyticsTabs
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import kotlinx.coroutines.launch

@Composable
fun OverviewScreen(
    // onCardClick: (String, List<ChartEntry>, @Composable (Modifier?, List<ChartEntry>) -> Unit) -> Unit,
    //navController: NavController,
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
){

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount =  { AnalyticsTabs.entries.size})
    val selectedTabIndex = remember{ derivedStateOf { pagerState.currentPage } }

    val charts = listOf(
        "Pie chart 1",
        "Pie chart 2",
        "Pie chart 3",
        "Pie chart 4",
        "Bar Graph"
    )


    Scaffold(
        topBar = { AnalyticsTopBar() },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)

        ) {

            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnalyticsTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {Text(currentTab.title)},
                        icon = {
                            Icon(
                                imageVector = if (selectedTabIndex.value == index)
                                    currentTab.selectedIcon else currentTab.unSelectedIcon,
                                contentDescription = "Tab Icon"
                            )
                        }
                    )

                }
            }



            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(AnalyticsTabs.entries[it].title)
                }
            }

//            val data = listOf(
//                PieSlice(
//                    color = Color.Blue,
//                    label = "Blue",
//                    value = 2.1,
//                    isTapped = false,
//                ),
//                PieSlice(
//                    color = Color.Red,
//                    label = "Red",
//                    value = 4.10,
//                    isTapped = false,
//                ),
//                PieSlice(
//                    color = Color.Green,
//                    label = "Green",
//                    value = 1.1,
//                    isTapped = false,
//                )
//            )
//            val sortedData = data.sortedByDescending { it.value }
//
//
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                contentPadding = PaddingValues(8.dp)
//            ) {
//
//            }
        }
    }
}
