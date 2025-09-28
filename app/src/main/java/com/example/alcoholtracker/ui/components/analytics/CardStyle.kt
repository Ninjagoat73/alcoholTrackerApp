package com.example.alcoholtracker.ui.components.analytics

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class CardStyle {
    object GridSquare : CardStyle()
    object Compact : CardStyle()
    object Large : CardStyle()
    object Landscape : CardStyle()
}

fun Modifier.applyCardStyle(style: CardStyle): Modifier = when (style) {
    CardStyle.GridSquare -> this
        .fillMaxWidth()
        .aspectRatio(1f)
        .padding(8.dp)

    CardStyle.Compact -> this
        .fillMaxWidth()
        .height(80.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)

    CardStyle.Large -> this
        .fillMaxWidth()
        .height(200.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)

    CardStyle.Landscape -> this
        .fillMaxWidth()
        .aspectRatio(2f)
        .padding(vertical = 8.dp, horizontal = 16.dp)
}