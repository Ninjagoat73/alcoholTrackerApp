package com.example.alcoholtracker.ui.components.progressbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alcoholtracker.data.model.UserDrinkLog


interface ProgressBarInterface {

    @Composable
    fun ProgressBarCard(userDrinkLogs: List<UserDrinkLog>)

    @Composable
    fun ProgressBar(score: Double)

    @Composable
    fun ProgressText(money: Double, count: Int, amount: Int)
}