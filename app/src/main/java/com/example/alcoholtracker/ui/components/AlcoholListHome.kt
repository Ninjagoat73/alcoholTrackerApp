package com.example.alcoholtracker.ui.components

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.compose.AlcoholTrackerTheme
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.DataTableScope
import com.seanproctor.datatable.material3.DataTable




@Composable
fun AlcoholListHome(userDrinkLogs: List<String>){
    ElevatedCard(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(12.dp))
        .height(300.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)) {

        var selectedRow by remember { mutableStateOf<Int?>(null) }

        DataTable(
            modifier = Modifier
                .fillMaxWidth(),
            columns = listOf(
            DataColumn(){
                HeaderText("Drink name")
            },
            DataColumn(){
                HeaderText("Price")
            },
            DataColumn(){
                HeaderText("Alcohol Percentage")
            },
        )) {
            row {
                onClick = { selectedRow = 0 }
                cell { Text("Cell A1") }
                cell { Text("Cell B1") }
                cell { Text("Cell C1") }
            }
            row {
                onClick = { selectedRow = 1 }
                cell { Text("Cell A2") }
                cell { Text("Cell B2") }
                cell { Text("Cell C2") }
            }
        }


        /*Row(modifier = Modifier.height(30.dp)
            .fillMaxWidth()
            .padding(4.dp)
            .padding(horizontal = 6.dp)
            )
            {
            RowText("Drink")
            Spacer(modifier = Modifier.padding(5.dp))
            VerticalDivider()
            Spacer(modifier = Modifier.padding(5.dp))
            RowText("Price")
        }

        LazyColumn(modifier = Modifier
            .fillMaxHeight()
        ) {
            items(userDrinkLogs ) {drink ->
                Text(text = "$drink is your thing",
                modifier = Modifier.padding(10.dp))
            }
        }
        */
    }
}

@Composable
fun HeaderText(text: String){
    Text(text = text,
        color = MaterialTheme.colorScheme.tertiary,
        )
}



val drinks = listOf(
    "Wine",
    "Beer",
    "Cabernet Sauvignon",
    "Merlot",
    "Pinot Noir",
    "Chardonnay",
    "Sauvignon Blanc",
    "Riesling",
    "Zinfandel",
    "Syrah",
    "Malbec",
    "Prosecco",
    "Champagne",
    "Rosé",
    "Moscato",
    "Pinot Grigio",
    "Shiraz",
    "IPA (India Pale Ale)",
    "Lager",
    "Stout",
    "Porter",
    "Pilsner",
    "Wheat Beer",
    "Pale Ale",
    "Sour Beer",
    "Amber Ale",
    "Brown Ale",
    "Belgian Tripel",
    "Hefeweizen",
    "Dunkel",
    "Bock",
    "Kolsch",
    "Thing",
    "stuff",
    "Stuff2"
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    AlcoholTrackerTheme {
        AlcoholListHome(drinks)
    }
}