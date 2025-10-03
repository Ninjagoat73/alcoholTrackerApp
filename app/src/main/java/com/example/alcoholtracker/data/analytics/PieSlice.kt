package com.example.alcoholtracker.data.analytics

import androidx.compose.ui.graphics.Color

data class PieSlice(
    override val label: String,
    override val value: Double,
    override val color: Color,
    val isTapped: Boolean = false
) : ChartEntry