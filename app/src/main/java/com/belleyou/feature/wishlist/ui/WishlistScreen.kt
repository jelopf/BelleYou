package com.belleyou.feature.wishlist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.model.Product
import com.belleyou.feature.wishlist.WishlistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = koinViewModel(),
    onProductClick: (String) -> Unit = {},
    onGoToCatalog: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    WishlistScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onFavoriteToggle = viewModel::toggleFavorite,
        onAddToCart = viewModel::addToCart,
        onSelectWishlist = viewModel::selectWishlist,
        onGoToCatalog = onGoToCatalog
    )
}

@Composable
fun WishlistScreenContent(
    uiState: WishlistUiState,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onSelectWishlist: (Int) -> Unit,
    onGoToCatalog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Custom Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "ИЗБРАННОЕ",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
                    .clickable { /* Share action */ },
                tint = colorResource(id = R.color.belle_black)
            )
        }

        if (uiState.products.isEmpty()) {
            EmptyWishlistContent(
                recommendedProducts = uiState.recommendedProducts,
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteToggle = onFavoriteToggle,
                onGoToCatalog = onGoToCatalog
            )
        } else {
            FilledWishlistContent(
                uiState = uiState,
                onProductClick = onProductClick,
                onFavoriteToggle = onFavoriteToggle,
                onAddToCart = onAddToCart,
                onSelectWishlist = onSelectWishlist
            )
        }
    }
}

@Composable
private fun FilledWishlistContent(
    uiState: WishlistUiState,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onSelectWishlist: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Wishlists Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ВИШЛИСТЫ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp
            )
            Text(
                text = "Добавить вишлист",
                fontSize = 12.sp,
                color = colorResource(id = R.color.belle_black),
                modifier = Modifier.clickable { /* Add wishlist */ }
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            itemsIndexed(uiState.wishlists) { index, title ->
                val isSelected = index == uiState.selectedWishlistIndex
                OutlinedButton(
                    onClick = { onSelectWishlist(index) },
                    shape = RectangleShape,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = if (isSelected) colorResource(id = R.color.belle_blue_dark) else Color.LightGray
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) colorResource(id = R.color.belle_blue_dark).copy(alpha = 0.5f) else Color.Transparent,
                        contentColor = colorResource(id = R.color.belle_black)
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = title, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.products) { product ->
                Column {
                    ProductCard(
                        product = product,
                        isFavorite = true,
                        showFavoriteIcon = true,
                        showInCartButton = true,
                        onClick = { onProductClick(product.id) },
                        onFavoriteClick = { onFavoriteToggle(product.id) },
                        onAddToCartClick = { onAddToCart(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyWishlistContent(
    recommendedProducts: List<Product>,
    favorites: Set<String>,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onGoToCatalog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "ВЫ ЕЩЕ НИЧЕГО НЕ ДОБАВИЛИ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Добавляйте понравившиеся товары в Избранное, чтобы посмотреть или купить их позже",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onGoToCatalog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.belle_blue).copy(alpha = 0.5f),
                contentColor = colorResource(id = R.color.belle_black)
            ),
            shape = RectangleShape,
            elevation = null
        ) {
            Text(text = "ПЕРЕЙТИ В КАТАЛОГ", fontSize = 14.sp, letterSpacing = 1.sp)
        }
        
        Spacer(modifier = Modifier.height(60.dp))
        
        if (recommendedProducts.isNotEmpty()) {
            ProductHorizontalRow(
                title = "Рекомендуем",
                products = recommendedProducts,
                favorites = favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = onGoToCatalog
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenEmptyPreview() {
    WishlistScreenContent(
        uiState = WishlistUiState(
            products = emptyList(),
            recommendedProducts = listOf(
                Product("1", "Товар 1", "123", 1000, description = "", category = "", imageUrl = "")
            )
        ),
        onProductClick = {},
        onFavoriteToggle = {},
        onAddToCart = {},
        onSelectWishlist = {},
        onGoToCatalog = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenFilledPreview() {
    WishlistScreenContent(
        uiState = WishlistUiState(
            wishlists = listOf("ИЗБРАННОЕ", "НА ДР", "НА 8 МАРТА"),
            products = listOf(
                Product("1", "Брюки из батиста", "123", 12999, description = "", category = "", imageUrl = ""),
                Product("2", "Трусы-слипы", "456", 599, description = "", category = "", imageUrl = "")
            )
        ),
        onProductClick = {},
        onFavoriteToggle = {},
        onAddToCart = {},
        onSelectWishlist = {},
        onGoToCatalog = {}
    )
}
