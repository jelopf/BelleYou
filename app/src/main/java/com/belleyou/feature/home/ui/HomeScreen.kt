package com.belleyou.feature.home.ui

import com.belleyou.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belleyou.core.designsystem.components.layout.HomeCategoryBanner
import com.belleyou.core.designsystem.components.layout.MainPromoBanner
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.model.Product
import com.belleyou.feature.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onProductClick: (Int) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onCategoryClick = onCategoryClick,
        onFavoriteToggle = viewModel::toggleFavorite
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onProductClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoriteToggle: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Header with Logo and Search Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Keep the logo as it's a key UI element, but we could placeholder it too if needed
                Image(
                    painter = painterResource(id = R.drawable.belle_you_home),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(24.dp)
                        .align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        item {
            MainPromoBanner(
                title = "Только 3 дня",
                subtitle = "Скидки на бестселлеры",
                painter = null // Removed drawable resource
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Новинки",
                products = uiState.products.take(6),
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("new") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            HomeCategoryBanner(
                title = "Нижнее белье",
                painter = null, // Removed drawable resource
                onClick = { onCategoryClick("Нижнее белье") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            HomeCategoryBanner(
                title = "Купальники",
                painter = null, // Removed drawable resource
                onClick = { onCategoryClick("Купальники") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            HomeCategoryBanner(
                title = "Одежда",
                painter = null, // Removed drawable resource
                onClick = { onCategoryClick("Одежда") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Сейчас ищут",
                products = uiState.products.reversed().take(6),
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("trending") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            HomeCategoryBanner(
                title = "Купальники Аврора / Base Swim",
                painter = null, // Removed drawable resource
                onClick = { onCategoryClick("swimwear_special") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Рекомендуем",
                products = uiState.products.shuffled().take(6),
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("recommended") }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(
            products = listOf(
                Product(
                    id = 1,
                    name = "Лонгслив",
                    article = "123",
                    price = 1000,
                    description = "Описание",
                    category = "Категория",
                    imageUrl = ""
                )
            )
        ),
        onProductClick = {},
        onCategoryClick = {},
        onFavoriteToggle = {}
    )
}