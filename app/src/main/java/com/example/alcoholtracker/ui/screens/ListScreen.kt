package com.example.alcoholtracker.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.alcohollist.AlcoholListComposable
import com.example.alcoholtracker.ui.components.alcohollist.AlcoholListType
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

@Composable
fun ListScreen(
    onFABClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = { AddButton(onFABClick) },
        modifier = Modifier.fillMaxSize()) { innerPadding ->

            Surface(modifier = Modifier.padding(innerPadding)) {

                AlcoholListComposable(
                    authViewModel,
                    userDrinkLogViewModel,
                    AlcoholListType.FULL
                )
            }
    }
}
