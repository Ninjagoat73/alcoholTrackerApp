package com.example.alcoholtracker.ui.components.alcohollist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel

enum class AlcoholListType{FULL, HOME}

@Composable
fun AlcoholListComposable(
    authViewModel: AuthViewModel,
    userDrinkLogViewModel: UserAndUserDrinkLogViewModel,
    listType: AlcoholListType
) {

    val userId by authViewModel.getUserID()
    val drinkLogs by userDrinkLogViewModel.getDrinkLogs(userId!!).collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn {

            item {
                Header(listType)
            }

            items(
                items = drinkLogs,
                key = { it.logId })
            { item ->
                SwipeToDismissItem(
                    item = item,
                    onRemove = { userDrinkLogViewModel.deleteDrink(item) },
                    modifier = Modifier.animateItem(tween(200)),
                    listType = listType
                )
            }
        }

    }
}