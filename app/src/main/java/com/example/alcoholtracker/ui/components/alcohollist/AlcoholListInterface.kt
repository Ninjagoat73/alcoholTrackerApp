package com.example.alcoholtracker.ui.components.alcohollist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

interface AlcoholListInterface {
    @Composable
    fun AlcoholListComposable(authViewModel: AuthViewModel,
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel)

    @Composable
    fun Header()

    @Composable
    fun SwipeToDismissItem(
        item: UserDrinkLog,
        onRemove: () -> Unit,
        modifier: Modifier
    )
}