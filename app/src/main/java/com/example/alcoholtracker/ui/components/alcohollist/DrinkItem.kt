package com.example.alcoholtracker.ui.components.alcohollist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alcoholtracker.data.model.UserDrinkLog
import java.time.format.DateTimeFormatter

@Composable
fun DrinkItem(item: UserDrinkLog,
              listType: AlcoholListType){

    val formatter = DateTimeFormatter.ofPattern("dd-MM")
    val date  = item.date.toLocalDate().format(formatter)

    ListItem(
        headlineContent = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = item.name,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.amount.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.cost.toString(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.alcoholPercentage.toString(),
                    modifier = Modifier.weight(1f)
                )
                if (listType == AlcoholListType.FULL) {
                    Text(
                        text = date.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}

@Composable
fun ExpandedDrinkItem(item: UserDrinkLog){
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val time = item.date.toLocalTime().format(timeFormatter)

    Column(modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 4.dp)) {
        Text("Recipient: ${item.recipient ?: "None"}")
        Text("Category: ${item.category}")
        Text("Time: $time")
    }

}
