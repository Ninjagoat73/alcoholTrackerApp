package com.example.alcoholtracker.ui.components.analytics.graphs

import androidx.compose.runtime.Composable

sealed interface ChartType {
    @Composable
    fun Render()
}