package com.example.alcoholtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AlcoholTrackerTheme


@Composable
fun ArcBackground() {

    val primaryContainer = MaterialTheme.colorScheme.primaryContainer

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
    ) {
        val width = size.width
        val height = size.height
        val path = Path()
        with(path) {
            moveTo(width, height * 1.2f)
            quadraticTo(width, height / 10f, 0f, height * 0.03f)
            lineTo(0f, 0f)
            lineTo(width, 0f)
            close()
        }
        drawPath(path, color = primaryContainer)
    }
}


@Preview (showBackground = true)
@Composable
fun TopRightQuarterCirclePreview(){
    AlcoholTrackerTheme {
        Box(modifier = Modifier.fillMaxSize()) {
        ArcBackground()
        }
    }
}
