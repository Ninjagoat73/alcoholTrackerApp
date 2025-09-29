@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.alcoholtracker.ui.components.analytics

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.os.trace
import androidx.room.util.TableInfo


@Composable
fun <T> AnalyticsCard(
    state: AnalyticsState,
    title: String,
    style: CardStyle,
    data: List<T>,
    onClicked: () -> Unit,
    content: @Composable (Modifier) -> Unit,
    ){

    SharedTransitionLayout {
        AnimatedContent(
            state,
        ) { current ->
            when(current){
                AnalyticsState.NONE -> {
                    MainContent(
                        style = style,
                        content = content,
                        title = title,
                        onShowDetails = { onClicked() },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
                AnalyticsState.DETAILS -> {
                    SmallDetailsContent(
                        style = style,
                        content = content,
                        title = title,
                        onBack = {  },
                        onShowDetails = {  },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout)
                }
                AnalyticsState.FULLSCREEN -> {
                    DetailsContent(
                        style = style,
                        content = content,
                        title = title,
                        onBack = {  },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }

        }
    }

}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    style: CardStyle,
    content: @Composable (Modifier) -> Unit,
    title: String,
    onShowDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    Card(
        modifier = Modifier
            .applyCardStyle(style)
            .clickable(
                onClick = {
                    onShowDetails()
                }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(

        ) {

            with(sharedTransitionScope){
                Text(title,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ))
                content( Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "chart"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
            }
        }

    }
}

@Composable
private fun SmallDetailsContent(
    modifier: Modifier = Modifier,
    style: CardStyle,
    content: @Composable (Modifier) -> Unit,
    title: String,
    onBack: () -> Unit,
    onShowDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    Card(
        modifier = Modifier.padding(top = 200.dp, start = 16.dp, end = 16.dp)
    ) {
        with(sharedTransitionScope){
            Text(title,
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ))
            content(
                Modifier.sharedElement(
                    rememberSharedContentState(key = "chart"),
                    animatedVisibilityScope = animatedVisibilityScope
                )

            )
        }
    }
}

@Composable
private fun DetailsContent(
    modifier: Modifier = Modifier,
    style: CardStyle,
    content: @Composable (Modifier) -> Unit,
    title: String,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){

}
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    device = "spec:width=411dp,height=891dp,dpi=420"
//
//)
//@Composable
//fun CardPreview(){
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ){
//        items(1){index ->
//            AnalyticsCard(
//                title = "Card $index",
//                style = CardStyle.GridSquare
//            ) {
//                Text("I am card $index")
//            }
//        }
//
//    }
//
//}

