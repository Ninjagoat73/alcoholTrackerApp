package com.example.alcoholtracker.data.analytics

import androidx.compose.ui.graphics.Color

interface ChartEntry {
    val label: String
    val value: Double
    val color: Color
}