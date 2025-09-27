package com.example.alcoholtracker.ui.components.analytics.graphs


import android.R.color.white
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate

import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.alcoholtracker.data.analytics.PieSlice
import com.example.compose.AlcoholTrackerTheme

@Composable
fun PieChart(
    data: List<PieSlice>,
    modifier: Modifier = Modifier,
    radius: Float = 500f,
    innerRadius: Float = 250f,
    transparentWidth:Float = 70f
){
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var data by remember {
        mutableStateOf(data)
    }

    var isCenterTapped by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(key1 = true) {

                }
        ){
            val width = size.width
            val height = size.height
            circleCenter = Offset(x=width/2f, y= height/2f)

            val totalValue = data.sumOf {
                it.value
            }

            val anglePerValue = 360f/totalValue
            var currentStartAngle = -90f

            data.forEach { pieSlice ->
                val scale = if (pieSlice.isTapped) 1.1f else 1.0f
                val angleToDraw = (pieSlice.value * anglePerValue).toFloat()
                scale(scale){
                    drawArc(
                        color = pieSlice.color,
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius*2f,
                            height = radius*2f
                        ),
                        topLeft = Offset(
                            x=(width-radius*2f)/2f,
                            (height-radius*2f)/2f
                        )
                    )
                    currentStartAngle += angleToDraw
                }



                var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                var factor = 1f
                if (rotateAngle > 0f){
                    rotateAngle = (rotateAngle +180) % (360f)
                    factor = -0.92f
                }

                val percentage = (pieSlice.value/totalValue.toFloat()*100).toInt()

                drawIntoCanvas { canvas ->

                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        textSize = 48f
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                    }

                    if (percentage>3) {
                        rotate(rotateAngle) {

                            canvas.nativeCanvas.drawText(
                                "$percentage %",
                                circleCenter.x,
                                circleCenter.y + (radius - (radius - innerRadius) / 2f) * factor,
                                paint,
                            )
                        }
                    }
                }
            }
            drawIntoCanvas { canvas ->
                val frameworkPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = Color.White.copy(alpha = 0.6f).toArgb()
                    setShadowLayer(10f, 0f, 0f, Color.Gray.toArgb())
                }


                canvas.nativeCanvas.drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    frameworkPaint
                )
            }

                drawCircle(
                color = Color.White.copy(alpha = 0.2f),
                radius = innerRadius + transparentWidth / 2f,
                center = circleCenter,
                style = Stroke(width = transparentWidth)
            )
        }
        Text(
            "Not used",
            modifier = Modifier
                .width(Dp(innerRadius / 1.5f))
                .padding(25.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PiePreview(){

    val data = listOf(
        PieSlice(
            color = Color.Blue,
            label = "Blue",
            value = 2.1,
            isTapped = false,
        ),
        PieSlice(
        color = Color.Red,
        label = "Red",
        value = 4.10,
        isTapped = false,
        ),
        PieSlice(
            color = Color.Green,
            label = "Green",
            value = 1.1,
            isTapped = false,
        ),
        PieSlice(
            color = Color.Cyan,
            label = "Green",
            value = 3.2,
            isTapped = false,
        ),
        PieSlice(
            color = Color.Yellow,
            label = "Green",
            value = 4.5,
            isTapped = false,
        )

    )

    val sortedData = data.sortedByDescending { it.value }


    AlcoholTrackerTheme {
        PieChart(sortedData)
    }


}


fun Float.to360(): Float = ((this % 360f) + 360f) % 360f