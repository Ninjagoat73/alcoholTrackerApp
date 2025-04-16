package com.example.alcoholtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.alcoholtracker.ui.components.BottomNavigationBar
import com.example.alcoholtracker.ui.navigation.Screen
import com.example.alcoholtracker.ui.screens.AddDrinkScreen
import com.example.alcoholtracker.ui.screens.AnalyticsScreen
import com.example.alcoholtracker.ui.screens.HomeScreen
import com.example.alcoholtracker.ui.screens.ListScreen
import com.example.alcoholtracker.ui.screens.ProfileScreen
import com.example.alcoholtracker.ui.screens.SignInScreen
import com.example.alcoholtracker.ui.viewmodel.ProgressBarViewModel

import com.example.compose.AlcoholTrackerTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcoholTrackerTheme {
                MainScreen()
            }
            }
        }
    }

@Composable
fun MainScreen() {

    val progressBarViewModel: ProgressBarViewModel = hiltViewModel()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        val graph =
            navController.createGraph(startDestination = Screen.SignIn.rout) {
                composable(route = Screen.List.rout) {
                    ListScreen()
                }
                composable(route = Screen.Analytics.rout) {
                    AnalyticsScreen()
                }
                composable(route = Screen.Home.rout,) {
                    HomeScreen(navController, progressBarViewModel)
                }
                composable(route = Screen.Profile.rout) {
                    ProfileScreen()
                }
                composable(route = Screen.AddDrink.rout){
                    AddDrinkScreen(navController)
                }
                composable(route = Screen.SignIn.rout){
                    SignInScreen()
                }
                composable(route = Screen.SignUp.rout){

                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}
