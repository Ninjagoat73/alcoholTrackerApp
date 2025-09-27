package com.example.alcoholtracker.data.analytics

import androidx.compose.ui.graphics.Color

data class PieSlice(
    val color: Color,
    val label: String,
    val value: Double,
    val isTapped: Boolean = false

)