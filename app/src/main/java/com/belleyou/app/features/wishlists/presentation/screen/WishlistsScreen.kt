package com.belleyou.app.features.wishlists.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.features.product.domain.model.Product
import com.belleyou.app.ui.components.HeaderBelleYou
import com.belleyou.app.ui.components.ProductCard
import kotlinx.coroutines.launch

@Composable
fun WishlistsScreen() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Логотип
        HeaderBelleYou(
            modifier = Modifier.padding(top = 12.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp)
                .height(0.6.dp),
            color = colorResource(R.color.belle_brown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Заголовок
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.favorites_screen_title),
                fontSize = 16.sp
            )
            Icon(
                painter = painterResource(R.drawable.ic_favorite_list_add),
                contentDescription = "Добавить вишлист",
                modifier = Modifier
                    .clickable { }
                    .size(20.dp),
                tint = colorResource(R.color.belle_brown)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Список вишлистов
        WishlistRow(wishlists = listOf("избранное ❤️", "на пляж 🌴", "на каждый день ☀️"))

        Spacer(modifier = Modifier.height(12.dp))

        // Иконка "Поделиться"
        Icon(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = "Поделиться",
            modifier = Modifier
                .clickable { }
                .align(Alignment.Start)
                .padding(start = 18.dp)
                .size(20.dp),
            tint = colorResource(id = R.color.belle_brown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        //Карточки
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            fakeProducts.take(2).forEach { product ->

                ProductCard(
                    product = product,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun WishlistRow(
    wishlists: List<String>
) {
    var selected by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    //Вишлисты
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(wishlists) { index, item ->
                WishlistButton(
                    title = item,
                    selected = index == selected,
                    onClick = { selected = index }
                )
            }
        }
        // Левая стрелка
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 4.dp)
                .size(12.dp)
                .clickable {
                    scope.launch {
                        listState.animateScrollBy(-200f)
                    }
                },
            tint = colorResource(R.color.belle_gray)
        )
        // Правая стрелка
        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp)
                .size(12.dp)
                .clickable {
                    scope.launch {
                        listState.animateScrollBy(200f)
                    }
                },
            tint = colorResource(R.color.belle_gray)
        )
    }
}

@Composable
private fun WishlistButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    // Параметры сегмента кнопки
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(
            1.dp,
            if (selected) colorResource(R.color.belle_brown)
            else colorResource(R.color.belle_gray)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(R.color.belle_brown)
        ),
        contentPadding = PaddingValues(horizontal = 5.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistsScreenPreview() {
    WishlistsScreen()
}