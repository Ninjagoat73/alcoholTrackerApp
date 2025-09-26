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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    progressBarViewModel: ProgressBarViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel = hiltViewModel(),
){
    val userId by authViewModel.getUserID()
    val userDrinkLogs by userDrinkLogViewModel.getDrinkLogs(userId!!).collectAsState()
    val twoDayDrinkLogs by userDrinkLogViewModel.getTwoDaySummary(userId!!).collectAsState()
    var showDialog by remember { mutableStateOf(false)}
    val currentType by progressBarViewModel.currentType.collectAsState()

    val moneyTarget by  progressBarViewModel.moneyTarget.collectAsState()
    val countTarget by progressBarViewModel.countTarget.collectAsState()
    val amountTarget by  progressBarViewModel.amountTarget.collectAsState()
    val currentTargets =
        mapOf(
            ProgressBarType.MONEY to moneyTarget,
            ProgressBarType.COUNT to countTarget,
            ProgressBarType.AMOUNT to amountTarget
        )

    val currentTarget = currentTargets[currentType]

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
                        currentTargets,
                        onDismiss = {showDialog = false},
                        onConfirm = { selectedType, selectedTarget ->
                            progressBarViewModel.updateType(selectedType.toString())
                            progressBarViewModel.updateTarget(selectedTarget, selectedType)
                            showDialog = false
                        })
                }

                progressBar.ProgressBarCard(
                    twoDayDrinkLogs,
                    target = currentTarget!!,
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

