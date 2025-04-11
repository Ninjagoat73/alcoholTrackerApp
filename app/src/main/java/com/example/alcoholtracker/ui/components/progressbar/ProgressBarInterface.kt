package com.example.alcoholtracker.ui.components.progressbar

import androidx.compose.runtime.Composable
import com.example.alcoholtracker.data.model.UserDrinkLog
import kotlinx.coroutines.flow.StateFlow


interface ProgressBarInterface {

    @Composable
    fun ProgressBarCard(
        logs: List<UserDrinkLog>,
        target: Double,
        onEditClick: () -> Unit)

    @Composable
    fun ProgressBar(calculatedScore: Float)

    @Composable
    fun ProgressText(money: Double, count: Int, amount: Int)

    fun progressCalculator(unCalculatedScore: Double,target: Double ): Float
}