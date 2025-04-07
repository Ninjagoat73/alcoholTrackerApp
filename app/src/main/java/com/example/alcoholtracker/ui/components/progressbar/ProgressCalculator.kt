package com.example.alcoholtracker.ui.components.progressbar

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import java.time.LocalDate

fun progressCalculator(logs: List<UserDrinkLog>): UserDrinkLogSummary{

    var summary = UserDrinkLogSummary(0.0, 0, 0.0)

    for (log in logs){
        if (log.date == LocalDate.now() || log.date == LocalDate.now().minusDays(1)) {
            summary.totalCost += log.cost
            summary.drinkCount += 1
            summary.totalAmount += log.amount

        }
    }

    return summary
}