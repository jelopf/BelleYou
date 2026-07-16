package com.belleyou.feature.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.HeaderBelleYouWithBack
import com.belleyou.core.model.Product
import com.belleyou.feature.category.CategoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(categoryName) {
        viewModel.load(categoryName)
    }

    CategoryScreenContent(
        categoryName = categoryName,
        uiState = uiState,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onFavoriteClick = { productId -> viewModel.toggleFavorite(productId) }
    )
}

@Composable
fun CategoryScreenContent(
    categoryName: String,
    uiState: CategoryUiState,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    val products = uiState.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeaderBelleYouWithBack(
            onBackClick = onBackClick,
            title = categoryName,
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Filter",
                    modifier = Modifier.size(20.dp).clickable { /* Filter action */ },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Sort",
                    modifier = Modifier.size(20.dp).clickable { /* Sort action */ },
                    tint = Color.Black
                )
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = products,
                span = { index, _ ->
                    val isFullWidth = (index + 1) % 5 == 0
                    GridItemSpan(if (isFullWidth) 2 else 1)
                }
            ) { _, product ->
                ProductCard(
                    product = product,
                    isFavorite = uiState.favorites.contains(product.id),
                    showFavoriteIcon = true,
                    showCartAction = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onProductClick(product.id)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(product.id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreenContent(
        categoryName = "Новинки",
        uiState = CategoryUiState(
            products = listOf(
                Product(
                    id = 1,
                    name = "Майка из хлопка",
                    article = "BY01",
                    price = 3999,
                    description = "",
                    category = "Новинки",
                    imageUrl = ""
                ),
                Product(
                    id = 2,
                    name = "Блуза из батиста",
                    article = "BY02",
                    price = 11999,
                    description = "",
                    category = "Новинки",
                    imageUrl = ""
                )
            )
        ),
        onBackClick = {},
        onProductClick = {},
        onFavoriteClick = {}
    )
}