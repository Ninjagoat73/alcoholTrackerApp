package com.example.alcoholtracker

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.alcoholtracker.domain.logic.handlers.BeerHandler
import com.example.alcoholtracker.ui.components.BottomNavigationBar
import com.example.alcoholtracker.ui.navigation.Screen
import com.example.alcoholtracker.ui.screens.AddDrinkScreen
import com.example.alcoholtracker.ui.screens.AnalyticsScreen
import com.example.alcoholtracker.ui.screens.HomeScreen
import com.example.alcoholtracker.ui.screens.ListScreen
import com.example.alcoholtracker.ui.screens.ProfileScreen
import com.example.alcoholtracker.ui.screens.SignInScreen
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.ProgressBarViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

import com.example.compose.AlcoholTrackerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        setContent {
            AlcoholTrackerTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                val userId by authViewModel.userId.collectAsState()

                if (userId != null){
                    MainScreen()
                }else{
                    SignInScreen(authViewModel,
                            onGuestLogin = { authViewModel.signInAnonymously() })
                }
            }
        }

    }



    @Composable
    private fun updateUI(user: FirebaseUser? = null){
        val viewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            val userId = user?.uid
            println("userId: $userId")
            viewModel.setUserId(userId?:"")
        }

    }
    private fun reload() {
    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}

@Composable
fun MainScreen() {

    val authViewModel: AuthViewModel = hiltViewModel()
    val drinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()

    val userId by authViewModel.userId.collectAsState()

    LaunchedEffect(userId) {
        userId?.let { uid ->
            drinkLogViewModel.setUserId(uid)
        }
    }

    val bottomBarScreens = listOf(
        Screen.Home,
        Screen.List,
        Screen.Analytics,
        Screen.Profile,
        Screen.AddDrink
    )

    val progressBarViewModel: ProgressBarViewModel = hiltViewModel()
    val navController = rememberNavController()
    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in bottomBarScreens.map { it.rout }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            if (showBottomBar){
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->

        val graph =
            navController.createGraph(startDestination = Screen.Home.rout) {
                composable(route = Screen.List.rout) {
                    ListScreen(navController)
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


