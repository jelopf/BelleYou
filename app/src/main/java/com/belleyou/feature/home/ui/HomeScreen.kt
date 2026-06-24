package com.belleyou.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.CategoryRow
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onProductClick: (Int) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val filteredProducts = remember(uiState.products, uiState.searchQuery) {
        uiState.products.filter { product ->
            uiState.searchQuery.isBlank() ||
                    product.product.name.contains(uiState.searchQuery, true) ||
                    product.product.category.contains(uiState.searchQuery, true)
        }
    }

    val pagerState = rememberPagerState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            HeaderBelleYou()
        }

        item {
            HorizontalPager(
                count = 5,
                state = pagerState
            ) {
                Image(
                    painter = painterResource(R.drawable.slide),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(238.dp),
                    contentScale = ContentScale.Crop
                )
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = 5,
                modifier = Modifier.padding(vertical = 11.dp),
                activeColor = colorResource(id = R.color.black),
                inactiveColor = colorResource(id = R.color.belle_blue)
            )
        }

        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = android.R.color.white))
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    placeholder = { Text("Поиск товаров...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.onSearchQueryChange("") }
                            ) {
                                Icon(Icons.Default.Close, null)
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

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.categories),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )

                CategoryRow(
                    categories = listOf("Нижнее белье", "Одежда", "Купальники"),
                    images = listOf(
                        R.drawable.underwear_category,
                        R.drawable.swimwear_category,
                        R.drawable.slide
                    ),
                    onCategoryClick = onCategoryClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.home_screen_new),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        val rows = filteredProducts.chunked(2)
        itemsIndexed(rows) { index, rowItems ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { uiModel ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(
                            uiModel = uiModel,
                            isFavorite = uiState.favorites.contains(uiModel.product.id),
                            showFavoriteIcon = true,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onProductClick(uiModel.product.id)
                            },
                            onFavoriteClick = {
                                viewModel.toggleFavorite(
                                    uiModel.product.id
                                )
                            }
                        )
                    }
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            if (index != rows.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}