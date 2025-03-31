package com.example.alcoholtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AlcoholTrackerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight


@Composable
fun ProgressBarCard(score: Int){
    OutlinedCard(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(12.dp))
        .height(150.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        content = {
            ProgressBar(score)
        })
}

@Composable
fun ProgressBar(score: Int) {
    val progressFactor = remember(score) {
        mutableFloatStateOf(score * 0.005f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(50.dp))
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFFFFD54F),
                        Color(0xFFF57C00),
                        Color(0xFFB71C1C)
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFFFFD54F),
                            Color(0xFFF57C00),
                            Color(0xFFB71C1C)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(1f - progressFactor.floatValue)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .align(Alignment.CenterEnd)
        )
        Text(
            text = "${(score * 10)}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = if (progressFactor.floatValue > 0.5f) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}




@Preview(showBackground = true)
@Composable
fun Preview(){
    AlcoholTrackerTheme {
        ProgressBarCard(150)
    }
}