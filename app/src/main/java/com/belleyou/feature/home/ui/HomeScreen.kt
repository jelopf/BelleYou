package com.belleyou.feature.home.ui

import com.belleyou.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.belleyou.core.designsystem.components.layout.HomeCategoryBanner
import com.belleyou.core.designsystem.components.layout.MainPromoBanner
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.designsystem.components.layout.ShimmerPlaceholder
import com.belleyou.core.model.Product
import com.belleyou.feature.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onProductClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onCategoryClick = onCategoryClick,
        onFavoriteToggle = viewModel::toggleFavorite,
        onRetry = viewModel::retry
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onProductClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        when {
            uiState.isLoading -> {
                HomeLoadingState()
            }
            uiState.error != null -> {
                HomeErrorState(uiState.error, onRetry)
            }
            else -> {
                HomeSuccessState(
                    uiState = uiState,
                    onProductClick = onProductClick,
                    onCategoryClick = onCategoryClick,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
    }
}

@Composable
private fun HomeLoadingState() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ShimmerPlaceholder(modifier = Modifier.fillMaxWidth().height(400.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(3) {
                ShimmerPlaceholder(modifier = Modifier.size(140.dp, 200.dp))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        ShimmerPlaceholder(modifier = Modifier.fillMaxWidth().height(200.dp))
    }
}

@Composable
private fun HomeErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry, shape = RectangleShape) {
            Text(text = "ПОПРОБОВАТЬ СНОВА")
        }
    }
}

@Composable
private fun HomeSuccessState(
    uiState: HomeUiState,
    onProductClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit
) {
    // Stability: remember lists and states outside LazyColumn
    val newestProducts = remember(uiState.products) { 
        uiState.products.filter { it.imageUrl.startsWith("http") }.shuffled().take(6) 
    }
    val trendingProducts = remember(uiState.products) { 
        uiState.products.filter { it.imageUrl.startsWith("http") }.reversed().take(6) 
    }
    val recommendedProducts = remember(uiState.products) { 
        uiState.products.filter { it.imageUrl.startsWith("http") }.shuffled().take(6) 
    }

    val newestRowState = rememberLazyListState()
    val trendingRowState = rememberLazyListState()
    val recommendedRowState = rememberLazyListState()

    val underwearImage = remember(uiState.products) {
        uiState.products.filter { 
            it.category.contains("bele", ignoreCase = true) || 
            it.category.contains("белье", ignoreCase = true) ||
            it.category.contains("slipy", ignoreCase = true)
        }.randomOrNull()?.imageUrl
    }
    val swimwearImage = remember(uiState.products) {
        uiState.products.filter { 
            it.category.contains("kupal", ignoreCase = true) || 
            it.category.contains("купальник", ignoreCase = true) 
        }.randomOrNull()?.imageUrl
    }
    val clothingImage = remember(uiState.products) {
        uiState.products.filter { 
            it.category.contains("odezhda", ignoreCase = true) || 
            it.category.contains("одежда", ignoreCase = true) 
        }.randomOrNull()?.imageUrl
    }

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
                imageUrl = uiState.products.randomOrNull()?.imageUrl
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Новинки",
                products = newestProducts,
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("new") },
                state = newestRowState
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            HomeCategoryBanner(
                title = "Нижнее белье",
                imageUrl = underwearImage ?: "https://belleyou.ru/upload/iblock/88b/88b58a1f4b0075e7a9e576eae46de4ac.jpg",
                onClick = { onCategoryClick("nizhnee_bele") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            HomeCategoryBanner(
                title = "Купальники",
                imageUrl = swimwearImage ?: "https://belleyou.ru/upload/iblock/337/3375c1f54897a7d9e576eae46de4ac98.jpg",
                onClick = { onCategoryClick("kupalniki") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            HomeCategoryBanner(
                title = "Одежда",
                imageUrl = clothingImage ?: "https://belleyou.ru/upload/iblock/4a1/4a1f54897a7d9e576eae46de4ac98.jpg",
                onClick = { onCategoryClick("odezhda") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Сейчас ищут",
                products = trendingProducts,
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("trending") },
                state = trendingRowState
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            HomeCategoryBanner(
                title = "Купальники Аврора / Base Swim",
                imageUrl = swimwearImage ?: "https://belleyou.ru/upload/iblock/337/3375c1f54897a7d9e576eae46de4ac98.jpg",
                onClick = { onCategoryClick("kupalniki") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            ProductHorizontalRow(
                title = "Рекомендуем",
                products = recommendedProducts,
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = { onCategoryClick("recommended") },
                state = recommendedRowState
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
                    id = "1",
                    name = "Лонгслив",
                    article = "123",
                    price = 1000,
                    description = "Описание",
                    category = "Категория",
                    imageUrl = ""
                )
            ),
            isLoading = false
        ),
        onProductClick = {},
        onCategoryClick = {},
        onFavoriteToggle = {},
        onRetry = {}
    )
}
