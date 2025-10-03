package com.example.alcoholtracker.ui.components.analytics.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.alcoholtracker.data.analytics.PieSlice

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<PieSlice>,
){
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val totalValue = data.sumOf { it.value }
    val backgroundColor = MaterialTheme.colorScheme.background
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
                .pointerInput(data){
                    detectTapGestures { onTapped() }
                },

        ) {

            val radius = (size.minDimension / 2f) * 0.9f
            val innerRadius = radius * 0.5f
            val width = size.width
            val height = size.height
            val anglePerValue = 360f/totalValue
            var startAngle = -90f


            data.forEach { pieSlice ->
                val label = pieSlice.label
                val value = pieSlice.value
                val color = pieSlice.color
                val percentage = value/totalValue * 100
                val textLayoutResult = textMeasurer.measure(label)
                circleCenter = Offset(x=width/2f, y= height/2f)

                val sweepAngle = (pieSlice.value*anglePerValue).toFloat()


                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    size = Size(
                        width = radius * 2f,
                        height = radius * 2f
                    ),
                    topLeft = Offset(
                        x = (width - radius * 2f) / 2f,
                        y = (height - radius * 2f) / 2f
                    ),
                    useCenter = true
                )


                val midAngle = startAngle + sweepAngle / 2f
                var textRotation = midAngle + 90f
                val labelRadius = (radius + innerRadius) / 2f
                val midAngleRad = Math.toRadians(midAngle.toDouble())

                var factor = 1f
                if (midAngle > 45f && midAngle < 180f) {
                    textRotation = (textRotation+180)%360
                    factor = -1f
                }

                val textLayout = textMeasurer.measure(
                    text = "${(percentage).toInt()}%",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

                    )
                )

                rotate(textRotation){
                    drawText(
                        textLayoutResult = textLayout,
                        topLeft = Offset(
                            circleCenter.x - textLayout.size.width /2f,
                            circleCenter.y - ((radius + innerRadius) / 2f) * factor - textLayout.size.height / 2f
                        )
                    )
                }


                startAngle += sweepAngle

            }


            drawCircle(
                color = backgroundColor,
                radius = innerRadius
            )


        }
    }
}

fun onTapped(){

}

fun otherTapped(){

}

//@Preview(showBackground = true)
//@Composable
//fun PiePreview(){
//
//    val data = listOf(
//        PieSlice(
//            color = Color.Red,
//            label = "Red",
//            value = 2.0,
//            isTapped = false
//        ),
//        PieSlice(
//            color = Color.Blue,
//            label = "Blue",
//            value = 1.0,
//            isTapped = false
//        ),
//        PieSlice(
//            color = Color.Green,
//            label = "Green",
//            value = 3.0,
//            isTapped = false
//        ),
//                PieSlice(
//                color = Color.Yellow,
//        label = "Yellow",
//        value = 2.5,
//        isTapped = false
//    )
//    )
//    val sortedData = data.sortedByDescending { it.value }
//
//    MaterialTheme {
//        PieChart(
//            sortedData
//        )
//    }
//
//}

