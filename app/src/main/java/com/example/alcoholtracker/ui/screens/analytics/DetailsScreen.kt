package com.example.alcoholtracker.ui.screens.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alcoholtracker.data.analytics.ChartEntry

@Composable
fun <T: ChartEntry> DetailsScreen(
    title: String,
    data: List<T> ,
    //chart: @Composable (Modifier, List<T>) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(title)

        HorizontalDivider(
            modifier = Modifier.padding(top = 64.dp)
        )

//        chart(
//            Modifier.clickable(
//                onClick = {}
//            ),
//            data
//
//        )
        HorizontalDivider()

        Text("Hello")

        data.forEach { item ->
            Text(item.label)
        }

//        LazyColumn {
//            itemsIndexed(
//                items = data,
//                key = ){
//
//            }
//        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailsPreview(){
//
//    val data = listOf(
//        PieSlice(
//            color = Color.Blue,
//            label = "Blue",
//            value = 2.1,
//            isTapped = false,
//        ),
//        PieSlice(
//            color = Color.Red,
//            label = "Red",
//            value = 4.10,
//            isTapped = false,
//        ),
//        PieSlice(
//            color = Color.Green,
//            label = "Green",
//            value = 1.1,
//            isTapped = false,
//        )
//    )
//    val sortedData = data.sortedByDescending { it.value }
//
//    DetailsScreen(
//        "Test Chart",
//        sortedData
//    ) { modifier, slices ->
//        PieChart(
//            modifier = Modifier.heightIn(max = 200.dp),
//            data = slices
//        )
//    }
//}
