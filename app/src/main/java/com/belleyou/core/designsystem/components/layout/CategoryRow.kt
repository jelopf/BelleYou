package com.belleyou.core.designsystem.components.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import kotlinx.coroutines.launch

@Composable
fun CategoryRow(
    categories: List<String>,
    images: List<Int>,
    onCategoryClick: (String) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val contentPaddingHorizontal = 20.dp
    val spacing = 8.dp
    val columns = 3

    val itemWidth = (screenWidth - spacing * (columns - 1)) / columns
    val finalItemWidth = max(itemWidth, 80.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 4.dp)
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            contentPadding = PaddingValues(horizontal = contentPaddingHorizontal),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(categories) { index, category ->
                val imageRes = if (index < images.size) images[index] else R.drawable.swimwear_category
                Row(
                    modifier = Modifier
                        .width(finalItemWidth)
                        .height(48.dp)
                        .background(colorResource(id = R.color.belle_blue))
                        .clickable { onCategoryClick(category) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = category,
                        color = colorResource(id = R.color.black),
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .weight(1f)
                    )
                }
            }
        }

// Левая стрелка
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(colorResource(id = R.color.white))
                .clickable {
                    scope.launch {
                        listState.animateScrollBy(-200f)
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
                    .padding(start = 4.dp),
                tint = colorResource(R.color.belle_gray)
            )
        }

// Правая стрелка
        Box(
            modifier = Modifier
                //.size(height = 48.dp, width = 25.dp)
                .align(Alignment.CenterEnd)
                .background(colorResource(id = R.color.white))
                .clickable {
                    scope.launch {
                        listState.animateScrollBy(200f)
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = colorResource(R.color.belle_gray)
            )
        }
    }
}