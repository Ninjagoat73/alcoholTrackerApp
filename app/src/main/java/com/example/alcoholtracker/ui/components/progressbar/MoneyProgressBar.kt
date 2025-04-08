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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AlcoholTrackerTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import com.example.alcoholtracker.data.model.UserDrinkLog
import java.time.LocalDate


class MoneyProgressBar : ProgressBarInterface {
    @Composable
    override fun ProgressBarCard(
        logs: List<UserDrinkLog>,
        target: Double,
        onDismiss: () -> Unit,
        onConfirm: (ProgressBarType, Double) -> Unit
    ) {

        var showDialog by remember { mutableStateOf(false) }
        val summary = twoDaySummaryGetter(logs)


        OutlinedCard(modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .height(150.dp)
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ))


            {

                if (showDialog) {
                    ProgressBarEditDialog(
                        target,
                        ProgressBarType.MONEY,
                        onDismiss,
                        onConfirm)
                }

                Box {
                    IconButton(onClick = {
                        showDialog = true
                    }, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(20.dp)) {
                        Icon(Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onSurface)
                    }

                    ProgressText(summary.totalCost, summary.drinkCount, summary.totalAmount.toInt())

                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter)
                    {
                        ProgressBar(progressCalculator(summary.totalCost, target))
                    }
                }
            }
    }

    @Composable
    override fun ProgressBar(calculatedScore: Float) {
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
                    .fillMaxWidth(1f - calculatedScore)
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

    override fun progressCalculator(unCalculatedScore: Double, target: Double): Float {

        val score = unCalculatedScore/target

        return if (score in 0.0..1.0)
            score.toFloat()
        else if (score > 1.0)
            1.0F
        else
            0F
    }

    val mockLogs = listOf(
        UserDrinkLog(
            drinkId = 1,
            userId = 1,
            name = "Beer",
            cost = 5.0,
            alcoholPercentage = 5.0,
            amount = 500.0,
            category = "Beer",
            recipient = "Myself",
            date = LocalDate.now() // today
        ),
        UserDrinkLog(
            drinkId = 2,
            userId = 1,
            name = "Wine",
            cost = 10.0,
            alcoholPercentage = 12.5,
            amount = 250.0,
            category = "Wine",
            recipient = "Myself",
            date = LocalDate.now().minusDays(1) // yesterday
        ),
        UserDrinkLog(
            drinkId = 3,
            userId = 1,
            name = "Whiskey",
            cost = 15.0,
            alcoholPercentage = 40.0,
            amount = 50.0,
            category = "Spirits",
            recipient = "Friend",
            date = LocalDate.now().minusDays(2) // two days ago
        )
    )



/*
    @Preview(showBackground = true)
    @Composable
    fun Preview(){
        AlcoholTrackerTheme {
            ProgressBarCard(10.0,
                10.0,
                2000.0,
                200.0)
        }
    }
    */
}