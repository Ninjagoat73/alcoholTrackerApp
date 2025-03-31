package com.example.alcoholtracker.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.compose.AlcoholTrackerTheme
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.material3.DataTable
import java.time.LocalDate


@Composable
fun AlcoholListHome(userDrinkLogs: List<UserDrinkLog>){
    OutlinedCard(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(12.dp))
        .height(300.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
        ) {


        DataTable(
            modifier = Modifier
                .fillMaxWidth(),
            columns = listOf(
            DataColumn{
                HeaderText("Name")
            },
            DataColumn{
                HeaderText("Amount")
            },
            DataColumn{
                HeaderText("Price")
            },
            DataColumn{
                HeaderText("Alcohol %")
            },
        )) {
            for (drink in userDrinkLogs){
                row {
                    cell { Text(drink.name) }
                    cell { Text("${drink.amount}ml")}
                    cell { Text("${drink.cost}$") }
                    cell { Text("${drink.alcoholPercentage}%")}
                }
            }
        }
    }
}

@Composable
fun HeaderText(text: String){
    Text(text = text,
        color = MaterialTheme.colorScheme.tertiary,
        maxLines = 1,
        )
}


/*
val drinks = listOf(
    UserDrinkLog(
        drinkId = 1,
        name = "A",
        category = "B",
        logId = 0,
        userId = 0,
        cost = 3.0,
        alcoholPercentage = 2.0,
        amount = 1.0,
        recipient = "Me",
        date = LocalDate.now(),
    ),
    UserDrinkLog(
        drinkId = 1,
        name = "B",
        category = "B",
        logId = 1,
        userId = 0,
        cost = 3.0,
        alcoholPercentage = 2.0,
        amount = 1.0,
        recipient = "Me",
        date = LocalDate.now(),
    )

)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    AlcoholTrackerTheme {
        AlcoholListHome(drinks)
    }
}
*/
