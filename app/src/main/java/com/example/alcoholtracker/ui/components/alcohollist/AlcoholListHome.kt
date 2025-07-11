package com.example.alcoholtracker.ui.components.alcohollist



import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel
import com.example.alcoholtracker.ui.viewmodel.UserAndUserDrinkLogViewModel
import com.example.compose.AlcoholTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate



class AlcoholListHome: AlcoholListInterface {

    @Composable
    override fun AlcoholListComposable(
        authViewModel: AuthViewModel,
        userDrinkLogViewModel: UserAndUserDrinkLogViewModel
    )
    {

        val userId by authViewModel.getUserID()
        val drinkLogs by remember{userDrinkLogViewModel.getTwoDaySummary(userId!!)}.collectAsState()



        OutlinedCard(modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .height(300.dp)
            .fillMaxWidth()
            ) {

            Header()

            LazyColumn {
                items(
                    items = drinkLogs,
                    key = {it.logId})
                { item ->
                    SwipeToDismissItem(
                        item = item,
                        onRemove = {userDrinkLogViewModel.deleteDrink(item)},
                        modifier = Modifier.animateItem(tween(200))
                    )
                }
            }

        }
    }

    @Composable
    override fun Header(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                text = "Amount",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Price",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Alcohol %",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }


    @Composable
    override fun SwipeToDismissItem(
        item: UserDrinkLog,
        onRemove: () -> Unit,
        modifier: Modifier
    ){
        val coroutineScope = rememberCoroutineScope()
        val swipeToDismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = { state ->
                if (state == SwipeToDismissBoxValue.EndToStart){
                    coroutineScope.launch {
                        delay(500)
                        onRemove()
                    }
                    true
                } else false
            }
        )



        SwipeToDismissBox(
            state = swipeToDismissState,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val progress = swipeToDismissState.progress

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFFD32F2F)  // deep red
                                ),
                                startX = 0f,
                                endX = 600f + (400f*progress)
                            )
                        )
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(12.dp)
                    )
                }
            },
            modifier = modifier
        ) {
            Card(
                shape = RectangleShape,
            ){
                ListItem(
                    headlineContent = {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = item.name,
                                modifier = Modifier.weight(1f))
                            Text(text = item.amount.toString(),
                                modifier = Modifier.weight(1f))
                            Text(text = item.cost.toString(),
                                modifier = Modifier.weight(1f))
                            Text(text = item.alcoholPercentage.toString(),
                                modifier = Modifier.weight(1f))}
                        }
                )
                HorizontalDivider()
            }
        }
    }
}

