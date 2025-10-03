package com.example.alcoholtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.ui.graphics.vector.ImageVector

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
