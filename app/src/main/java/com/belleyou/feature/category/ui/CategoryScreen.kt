package com.belleyou.feature.category.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.HeaderBelleYouWithBack
import com.belleyou.core.designsystem.components.layout.ShimmerPlaceholder
import com.belleyou.core.model.Product
import com.belleyou.feature.category.CategoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(categoryName) {
        viewModel.load(categoryName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CategoryScreenContent(
            categoryName = categoryName,
            uiState = uiState,
            onBackClick = onBackClick,
            onProductClick = onProductClick,
            onFavoriteClick = { productId -> viewModel.toggleFavorite(productId) },
            onSortClick = { viewModel.toggleSortOverlay(true) },
            onRetry = { viewModel.load(categoryName) },
            onAddToCart = { productId ->
                // ViewModel logic could be added here if needed
                Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show()
            }
        )

        if (uiState.isSortOverlayVisible) {
            SortOverlay(
                currentOption = uiState.selectedSortOption,
                onOptionSelected = viewModel::setSortOption,
                onClose = { viewModel.toggleSortOverlay(false) }
            )
        }
    }
}

@Composable
fun CategoryScreenContent(
    categoryName: String,
    uiState: CategoryUiState,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onSortClick: () -> Unit,
    onRetry: () -> Unit,
    onAddToCart: (String) -> Unit
) {
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
                    modifier = Modifier.size(20.dp).clickable { onSortClick() },
                    tint = Color.Black
                )
            }
        )

        when {
            uiState.isLoading -> {
                CategoryLoadingState()
            }
            uiState.error != null -> {
                CategoryErrorState(uiState.error, onRetry)
            }
            else -> {
                CategoryGrid(
                    products = uiState.products,
                    favorites = uiState.favorites,
                    onProductClick = onProductClick,
                    onFavoriteClick = onFavoriteClick,
                    onAddToCart = onAddToCart
                )
            }
        }
    }
}

@Composable
private fun CategoryLoadingState() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(6) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ShimmerPlaceholder(modifier = Modifier.aspectRatio(0.75f).fillMaxWidth())
                ShimmerPlaceholder(modifier = Modifier.height(14.dp).fillMaxWidth(0.7f))
                ShimmerPlaceholder(modifier = Modifier.height(12.dp).fillMaxWidth(0.4f))
            }
        }
    }
}

@Composable
private fun CategoryErrorState(message: String, onRetry: () -> Unit) {
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
private fun CategoryGrid(
    products: List<Product>,
    favorites: Set<String>,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onAddToCart: (String) -> Unit
) {
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
                isFavorite = favorites.contains(product.id),
                showFavoriteIcon = true,
                showCartAction = true,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onProductClick(product.id)
                },
                onFavoriteClick = {
                    onFavoriteClick(product.id)
                },
                onAddToCartClick = {
                    onAddToCart(product.id)
                }
            )
        }
    }
}

@Composable
fun SortOverlay(
    currentOption: SortOption,
    onOptionSelected: (SortOption) -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "СОРТИРОВКА",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SortOptionItem("Без сортировки", currentOption == SortOption.NONE) { onOptionSelected(SortOption.NONE) }
            SortOptionItem("По новизне", currentOption == SortOption.NEWEST) { onOptionSelected(SortOption.NEWEST) }
            SortOptionItem("По возрастанию", currentOption == SortOption.PRICE_ASC) { onOptionSelected(SortOption.PRICE_ASC) }
            SortOptionItem("По убыванию", currentOption == SortOption.PRICE_DESC) { onOptionSelected(SortOption.PRICE_DESC) }
        }
    }
}

@Composable
fun SortOptionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(R.color.belle_blue_dark)
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, fontSize = 14.sp)
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
                    id = "1",
                    name = "Майка из хлопка",
                    article = "BY01",
                    price = 3999,
                    description = "",
                    category = "Новинки",
                    imageUrl = ""
                )
            ),
            isLoading = false
        ),
        onBackClick = {},
        onProductClick = {},
        onFavoriteClick = {},
        onSortClick = {},
        onRetry = {},
        onAddToCart = {}
    )
}