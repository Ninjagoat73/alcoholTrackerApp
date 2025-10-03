package com.example.alcoholtracker.ui.components


import android.net.http.SslCertificate.saveState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alcoholtracker.ui.navigation.Home
import com.example.alcoholtracker.ui.navigation.List
import com.example.alcoholtracker.ui.navigation.NavigationItem
import com.example.alcoholtracker.ui.navigation.Overview
import com.example.alcoholtracker.ui.navigation.Profile

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


@Composable
fun BottomNavigationBar(){

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", Home, Icons.Default.Home),
        TopLevelRoute("List", List, Icons.Default.FormatListNumbered),
        TopLevelRoute("Analytics", Overview, Icons.Default.Analytics),
        TopLevelRoute("Profile", Profile, Icons.Default.Person)

    )

    val navController = rememberNavController()


    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        topLevelRoutes.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {it.hasRoute(destination.route::class)} == true,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
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
                label = {Text(destination.name)}
            )
        }

    }
}