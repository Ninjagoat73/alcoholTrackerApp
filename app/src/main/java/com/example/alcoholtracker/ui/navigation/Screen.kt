package com.example.alcoholtracker.ui.navigation

sealed class Screen(val rout: String) {
    data object Home: Screen("HomeScreen")
    data object Profile: Screen("ProfileScreen")
    data object Analytics: Screen("AnalyticsScreen")
    data object List: Screen("ListScreen")
    data object AddDrink: Screen("AddDrinkScreen")
    data object SignIn: Screen("SignInScreen")
    data object SignUp: Screen("SignUpScreen")
}
