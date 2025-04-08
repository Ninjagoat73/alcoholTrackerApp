package com.example.alcoholtracker.ui.components.progressbar

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import com.example.compose.AlcoholTrackerTheme
import java.time.LocalDate

fun twoDaySummaryGetter(logs: List<UserDrinkLog>): UserDrinkLogSummary{

    var summary = UserDrinkLogSummary(0.0, 0, 0.0)

    for (log in logs){
        if (log.date == LocalDate.now() || log.date == LocalDate.now().minusDays(1)) {
            summary.totalCost += log.cost
            summary.drinkCount += 1
            summary.totalAmount += log.amount

        }
    }

    return summary
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBarEditDialog(currentType: ProgressBarType){

    val types by remember { mutableStateOf(listOf("Good", "Bad", "Worst"))}
    var selectedChoiceIndex by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = { TODO() },
        )
    {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp),)
        {
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
            ) {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {

                    Text(text = "Change Goal",)

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier)
                    {
                        Icon(Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    AlcoholTrackerTheme {
        ProgressBarEditDialog(ProgressBarType.MONEY)
    }
}



