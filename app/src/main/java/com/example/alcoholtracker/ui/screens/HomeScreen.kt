package com.example.alcoholtracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.alcoholtracker.ui.components.AddButton
import com.example.alcoholtracker.ui.components.alcohollist.AlcoholListHome
import com.example.alcoholtracker.ui.components.HomeTopBar
import com.example.alcoholtracker.ui.components.alcohollist.AlcoholListHomeWrapper
import com.example.alcoholtracker.ui.components.progressbar.AmountProgressBar
import com.example.alcoholtracker.ui.components.progressbar.CountProgressBar
import com.example.alcoholtracker.ui.components.progressbar.MoneyProgressBar
import com.example.alcoholtracker.ui.components.progressbar.ProgressBarEditDialog
import com.example.alcoholtracker.ui.components.progressbar.ProgressBarInterface
import com.example.alcoholtracker.ui.components.progressbar.ProgressBarType
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel


import com.example.alcoholtracker.ui.viewmodel.DrinkViewModel
import com.example.alcoholtracker.ui.viewmodel.ProgressBarViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    progressBarViewModel: ProgressBarViewModel,
    authViewModel: AuthViewModel = hiltViewModel(),
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
){
    val userId by authViewModel.getUserID()
    val userDrinkLogs by userDrinkLogViewModel.getDrinkLogs(userId!!).collectAsState()
    var showDialog by remember { mutableStateOf(false)}
    val currentType = progressBarViewModel.currentType.collectAsState().value
    val currentTarget = progressBarViewModel.currentTarget.collectAsState().value

    val progressBar: ProgressBarInterface = when (currentType) {
        ProgressBarType.MONEY -> MoneyProgressBar()
        ProgressBarType.COUNT -> CountProgressBar()
        ProgressBarType.AMOUNT -> AmountProgressBar()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {HomeTopBar() {} },
        floatingActionButton = {AddButton(navController)},
        modifier = Modifier.fillMaxSize()


    ) {
        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {

            Column {
                AlcoholListHomeWrapper()
                if (showDialog){
                    ProgressBarEditDialog(
                        currentType,
                        currentTarget,
                        onDismiss = {showDialog = false},
                        onConfirm = { selectedType, selectedTarget ->
                            progressBarViewModel.updateType(selectedType.toString())
                            progressBarViewModel.updateTarget(selectedTarget)
                            showDialog = false
                        })
                }

                progressBar.ProgressBarCard(
                    userDrinkLogs,
                    target = currentTarget,
                    onEditClick = {showDialog = true}
                )
            }
        }
    }
}



@Preview (showBackground = true)
@Composable
fun PreviewFunction(){

}

