package com.example.alcoholtracker.ui.components.alcohollist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

@Composable
fun AlcoholListFullWrapper(
    impl: AlcoholListInterface = AlcoholListFull(),
    authViewModel: AuthViewModel = hiltViewModel(),
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
) {
    impl.AlcoholListComposable(authViewModel, userDrinkLogViewModel)
}