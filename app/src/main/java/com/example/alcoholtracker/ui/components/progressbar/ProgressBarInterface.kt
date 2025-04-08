package com.example.alcoholtracker.ui.components.progressbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alcoholtracker.data.model.UserDrinkLog


interface ProgressBarInterface {

    @Composable
    fun ProgressBarCard(logs: List<UserDrinkLog>,
                        target: Double,
                        onDismiss: () -> Unit,
                        onConfirm: (ProgressBarType, Double) -> Unit)

    @Composable
    fun ProgressBar(calculatedScore: Float)

    @Composable
    fun ProgressText(money: Double, count: Int, amount: Int)

    fun progressCalculator(unCalculatedScore: Double,target: Double ): Float
}