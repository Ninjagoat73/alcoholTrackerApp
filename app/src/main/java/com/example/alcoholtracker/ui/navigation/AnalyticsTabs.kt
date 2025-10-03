package com.example.alcoholtracker.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.alcoholtracker.ui.screens.analytics.OverviewScreen
import kotlinx.coroutines.selects.select

enum class AnalyticsTabs(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {
    Overview(
        title = "Overview",
        selectedIcon = Icons.Outlined.Analytics,
        unSelectedIcon = Icons.Filled.Analytics,
    ),
    Finance(
        title = "Finance",
        selectedIcon = Icons.Outlined.Money,
        unSelectedIcon = Icons.Filled.Money,
    ),
    Health(
        title = "Health",
        selectedIcon = Icons.Outlined.MonitorHeart,
        unSelectedIcon = Icons.Filled.MonitorHeart,
    )
}