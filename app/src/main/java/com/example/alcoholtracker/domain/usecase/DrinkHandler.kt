package com.example.alcoholtracker.domain.usecase

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import javax.inject.Inject


class DrinkHandler @Inject constructor(
    private val logRepo: UserAndUserDrinkLogRepository,
    private val auth: FirebaseAuth
) {
    suspend fun createDrink(
        request: DrinkCreateRequest
    ){
        val userId = auth.currentUser?.uid ?: return
        val drink = UserDrinkLog(
            userId = userId,
            drinkId = 0,
            name = request.name,
            category = request.category,
            alcoholPercentage = request.abv,
            amount = request.volume,
            cost = request.cost,
            recipient = request.recipient,
            date = request.dateTime ?: LocalDateTime.now(),

        )
        logRepo.insertDrinkLog(drink)

    }

    suspend fun editDrink(
        drinkToUpdate: UserDrinkLog,
        request: DrinkCreateRequest

    ){
        val userId = auth.currentUser?.uid ?: return
        val drink = UserDrinkLog(
            logId = drinkToUpdate.logId,
            userId = userId,
            drinkId = 0,
            name = request.name,
            category = request.category,
            alcoholPercentage = request.abv,
            amount = request.volume,
            cost = request.cost,
            recipient = request.recipient,
            date = request.dateTime ?: LocalDateTime.now(),

            )
        logRepo.updateDrinkLog(drink)
    }

    suspend fun deleteDrink(
        drinkToDelete: UserDrinkLog
    ){
        logRepo.deleteDrinkLog(drinkToDelete)
    }
}

