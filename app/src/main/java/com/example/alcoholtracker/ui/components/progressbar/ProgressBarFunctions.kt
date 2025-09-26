package com.example.alcoholtracker.ui.components.progressbar


import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary

fun twoDaySummaryGetter(logs: List<UserDrinkLog>): UserDrinkLogSummary{
    val summary = UserDrinkLogSummary(0.0, 0, 0.0)

    for (log in logs){
            summary.totalCost += log.cost ?: 0.0
            summary.drinkCount += 1
            summary.totalAmount += log.amount ?: 0.0
    }
    return summary
}






