package com.example.alcoholtracker.ui.components.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.alcoholtracker.data.model.UserDrinkLog


class MoneyProgressBar : ProgressBarInterface {

    @Composable
    override fun ProgressBarCard(userDrinkLogs: List<UserDrinkLog>) {
        OutlinedCard(modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .height(150.dp)
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ))
            {
                ProgressText(10.0,
                    10,
                    1000,)

                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter)
                {
                    ProgressBar(10.0)
                }



            }
    }

    @Composable
    override fun ProgressBar(score: Double) {
        val progressFactor = remember(score) {
            mutableFloatStateOf((score * 0.005f).toFloat())
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
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

        }
    }

    @Composable
    override fun ProgressText(money: Double,
                              count: Int,
                              amount: Int, ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "$money$",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            Row {
                Text(text = "$count drinks, ${amount}ml",
                    modifier = Modifier.padding(end = 12.dp),
                    fontSize = 12.sp
                )
            }
        }



    }


    /*
    @Preview(showBackground = true)
    @Composable
    fun Preview(){
        AlcoholTrackerTheme {
            ProgressBarCard(null)
        }
    }*/
}