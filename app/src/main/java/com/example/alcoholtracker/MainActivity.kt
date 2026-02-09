package com.example.alcoholtracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alcoholtracker.ui.components.TopLevelRoute
import com.example.alcoholtracker.ui.navigation.AddDrink
import com.example.alcoholtracker.ui.navigation.Details
import com.example.alcoholtracker.ui.navigation.EditDrink
import com.example.alcoholtracker.ui.navigation.Home
import com.example.alcoholtracker.ui.navigation.List
import com.example.alcoholtracker.ui.navigation.Overview
import com.example.alcoholtracker.ui.navigation.Profile
import com.example.alcoholtracker.ui.screens.AddDrinkScreen
import com.example.alcoholtracker.ui.screens.AnalyticsScreen
import com.example.alcoholtracker.ui.screens.HomeScreen
import com.example.alcoholtracker.ui.screens.ListScreen
import com.example.alcoholtracker.ui.screens.ProfileScreen
import com.example.alcoholtracker.ui.screens.SignInScreen
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.example.compose.AlcoholTrackerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.vamsi.snapnotify.SnapNotifyProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        Log.e("problem", "i am starting")
        setContent {
            SnapNotifyProvider {
                AlcoholTrackerTheme {
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val userId by authViewModel.userId.collectAsState()


                    if (userId != null) {
                        MainScreen()
                    } else {
                        SignInScreen(
                            authViewModel,
                            onGuestLogin = { authViewModel.signInAnonymously() })
                    }
                }
            }
        }

    }
}

@Composable
fun MainScreen() {

    val authViewModel: AuthViewModel = hiltViewModel()
    val drinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
    val userId by authViewModel.userId.collectAsState()
    val bottomBarScreens = listOf(
        Home,
        List,
        Overview,
        Profile,
        AddDrink()
    )
    val topLevelRoutes = listOf(
        TopLevelRoute("Home", Home, Icons.Default.Home),
        TopLevelRoute("List", List, Icons.Default.FormatListNumbered),
        TopLevelRoute("Analytics", Overview, Icons.Default.Analytics),
        TopLevelRoute("Profile", Profile, Icons.Default.Person)

    )

    LaunchedEffect(userId) {
        userId?.let { uid ->
            drinkLogViewModel.setUserId(uid)
        }
    }

    val navController = rememberNavController()
//    val showBottomBar = navController
//        .currentBackStackEntryAsState() in bottomBarScreens

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                topLevelRoutes.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }

                                launchSingleTop = true

                                restoreState = true
                            }

                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = "Icon"
                            )
                        },
                        label = { Text(destination.name) }
                    )
                }

            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        )
        {
            composable<Home> {
                HomeScreen(
                    onFABClick = {
                        navController.navigate(AddDrink())
                    }
                )
            }
            composable<List> {
                ListScreen(
                    onFABClick = {
                        navController.navigate(AddDrink(null))
                    },
                    onEditClick = {
                        navController.navigate(AddDrink(it.logId))
                    }
                )
            }
            composable<AddDrink> {
                AddDrinkScreen(
                    onAddDrink = {
                        navController.popBackStack()
                    }
                )
            }
            composable<EditDrink> {

            }
            composable<Profile> {
                ProfileScreen()
            }
            composable<Overview> {
                AnalyticsScreen()
            }
            composable<Details> {

            }
        }

    }
}
