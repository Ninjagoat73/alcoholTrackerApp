package com.example.alcoholtracker.ui.navigation

sealed class Screen(val rout: String) {
    object Home: Screen("HomeScreen")
    object Profile: Screen("ProfileScreen")
    object Analytics: Screen("AnalyticsScreen")
    object List: Screen("ListScreen")
}
