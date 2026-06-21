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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.ui.components.CategoryRow
import com.belleyou.app.ui.components.HeaderBelleYou
import com.belleyou.app.ui.components.ProductCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedTextFieldDefaults

@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts = if (searchQuery.isBlank()) {
        fakeProducts
    } else {
        fakeProducts.filter { product ->
            product.name.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)
        }
    }
    val images = listOf(
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide,
        R.drawable.slide
    )
    val images_category = listOf(
        R.drawable.underwear_category,
        R.drawable.swimwear_category,
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

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Поиск товаров...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Очистить"
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.belle_under_header),
                unfocusedBorderColor = colorResource(id = R.color.belle_under_header),
                focusedTextColor = colorResource(id = R.color.belle_under_header),
                unfocusedTextColor = colorResource(id = R.color.belle_under_header),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.categories),
            color = colorResource(id = R.color.black),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start)
                .padding(horizontal = 24.dp)
        )
        CategoryRow(
            categories = listOf("Нижнее белье", "Одежда", "Купальники"),
            images = images_category,
            onCategoryClick = onCategoryClick
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            val rows = (filteredProducts.size + 1) / 2
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val firstIndex = row * 2
                    val secondIndex = firstIndex + 1

                    if (firstIndex < filteredProducts.size) {
                        val product = fakeProducts[firstIndex]

                        ProductCard(
                            product = filteredProducts[firstIndex],
                            modifier = Modifier.weight(1f),
                            showFavoriteIcon = true,
                            onClick = { onProductClick(product.id) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f))
                    }

                    if (secondIndex < filteredProducts.size) {
                        val product = fakeProducts[firstIndex]

                        ProductCard(
                            product = filteredProducts[secondIndex],
                            modifier = Modifier.weight(1f),
                            showFavoriteIcon = true,
                            onClick = { onProductClick(product.id) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f))
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