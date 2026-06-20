package com.belleyou.app.features.home.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belleyou.app.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.ui.components.HeaderBelleYou
import com.belleyou.app.ui.components.ProductCard

@Composable
fun HomeScreen(onProductClick: (Int) -> Unit = {}) {
    val images = listOf(
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide
    )
    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        HeaderBelleYou()
        HorizontalPager(
            count = images.size,
            state = pagerState
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Картинка",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(238.dp),
                contentScale = ContentScale.Crop
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = images.size,
            modifier = Modifier.padding(vertical = 11.dp),
            activeColor = colorResource(id = R.color.black),
            inactiveColor = colorResource(id = R.color.belle_blue)
        )

        /*Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = colorResource(id = R.color.belle_brown)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.home_screen_discount),
                    color = colorResource(id = R.color.white),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
                Button(
                    onClick = { /* действие */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.white),
                        contentColor = colorResource(id = R.color.black)
                    ),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(stringResource(id = R.string.home_screen_in))
                }
            }
        }*/
        Text(
            text = stringResource(id = R.string.home_screen_new),
            color = colorResource(id = R.color.black),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start)
                .padding(horizontal = 24.dp)
        )
        // Динамическая сетка карточек
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val rows = (fakeProducts.size + 1) / 2 // количество строк (округляем вверх)
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val firstIndex = row * 2
                    val secondIndex = firstIndex + 1

                    // Первая карточка в строке
                    if (firstIndex < fakeProducts.size) {
                        val product = fakeProducts[firstIndex]

                        ProductCard(
                            product = fakeProducts[firstIndex],
                            modifier = Modifier.weight(1f),
                            onClick = { onProductClick(product.id) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f)) // пустая заглушка, если товара нет
                    }

                    // Вторая карточка в строке
                    if (secondIndex < fakeProducts.size) {
                        val product = fakeProducts[firstIndex]

                        ProductCard(
                            product = fakeProducts[secondIndex],
                            modifier = Modifier.weight(1f),
                            // 3. То же самое для второй карточки в ряду
                            onClick = { onProductClick(product.id) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f)) // пустая заглушка
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}