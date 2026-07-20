package com.belleyou.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.model.Product
import com.belleyou.feature.cart.CartViewModel
import com.belleyou.feature.cart.domain.CartItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel(),
    onProductClick: (String) -> Unit = {},
    onGoToCatalog: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onGoToCatalog = onGoToCatalog,
        onToggleSelectAll = viewModel::toggleSelectAll,
        onToggleItemSelection = viewModel::toggleItemSelection,
        onIncreaseQuantity = viewModel::increaseQuantity,
        onDecreaseQuantity = viewModel::decreaseQuantity,
        onRemoveFromCart = viewModel::removeFromCart,
        onFavoriteToggle = viewModel::toggleFavorite
    )
}

@Composable
fun CartScreenContent(
    uiState: CartUiState,
    onProductClick: (String) -> Unit,
    onGoToCatalog: () -> Unit,
    onToggleSelectAll: (Boolean) -> Unit,
    onToggleItemSelection: (String, String) -> Unit,
    onIncreaseQuantity: (String, String) -> Unit,
    onDecreaseQuantity: (String, String) -> Unit,
    onRemoveFromCart: (String, String) -> Unit,
    onFavoriteToggle: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "КОРЗИНА",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
        }

        if (uiState.cartItems.isEmpty()) {
            EmptyCartContent(
                recommendedProducts = uiState.recommendedProducts,
                onProductClick = onProductClick,
                onGoToCatalog = onGoToCatalog,
                onFavoriteToggle = onFavoriteToggle
            )
        } else {
            FilledCartContent(
                uiState = uiState,
                onProductClick = onProductClick,
                onToggleSelectAll = onToggleSelectAll,
                onToggleItemSelection = onToggleItemSelection,
                onIncreaseQuantity = onIncreaseQuantity,
                onDecreaseQuantity = onDecreaseQuantity,
                onRemoveFromCart = onRemoveFromCart,
                onFavoriteToggle = onFavoriteToggle
            )
        }
    }
}

@Composable
private fun FilledCartContent(
    uiState: CartUiState,
    onProductClick: (String) -> Unit,
    onToggleSelectAll: (Boolean) -> Unit,
    onToggleItemSelection: (String, String) -> Unit,
    onIncreaseQuantity: (String, String) -> Unit,
    onDecreaseQuantity: (String, String) -> Unit,
    onRemoveFromCart: (String, String) -> Unit,
    onFavoriteToggle: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val allSelected = uiState.cartItems.isNotEmpty() && 
                uiState.selectedItems.size == uiState.cartItems.size

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = allSelected,
                onCheckedChange = onToggleSelectAll,
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.belle_black)
                )
            )
            Text(text = "Выбрать все", fontSize = 12.sp)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(uiState.cartItems) { index, item ->
                ProductCardCart(
                    item = item,
                    isSelected = (item.product.id to item.selectedSize) in uiState.selectedItems,
                    isFavorite = item.product.id in uiState.favorites,
                    onSelectionChange = { onToggleItemSelection(item.product.id, item.selectedSize) },
                    onIncrease = { onIncreaseQuantity(item.product.id, item.selectedSize) },
                    onDecrease = { onDecreaseQuantity(item.product.id, item.selectedSize) },
                    onDelete = { onRemoveFromCart(item.product.id, item.selectedSize) },
                    onFavoriteToggle = { onFavoriteToggle(item.product.id) },
                    onClick = { onProductClick(item.product.id) },
                    showDivider = index != uiState.cartItems.lastIndex
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                OrderSummarySection(uiState)
            }
        }

        // Checkout Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { /* Navigate to checkout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.belle_blue).copy(alpha = 0.5f),
                    contentColor = colorResource(R.color.belle_black)
                ),
                shape = RectangleShape,
                elevation = null
            ) {
                Text(text = "ПЕРЕЙТИ К ОФОРМЛЕНИЮ", fontSize = 14.sp, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun OrderSummarySection(uiState: CartUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "ВАШ ЗАКАЗ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Количество", fontSize = 12.sp, color = Color.Gray)
            Text(text = "${uiState.cartItems.sumOf { it.quantity }} ед.", fontSize = 12.sp)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "сумма заказа", fontSize = 12.sp, color = Color.Gray)
            val fullSum = uiState.cartItems.sumOf { (it.product.price ?: 0) * it.quantity }
            Text(text = "$fullSum ₽", fontSize = 12.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "ИТОГО", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = "${uiState.totalPrice} ₽", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun EmptyCartContent(
    recommendedProducts: List<Product>,
    onProductClick: (String) -> Unit,
    onGoToCatalog: () -> Unit,
    onFavoriteToggle: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "ВАША КОРЗИНА ПУСТА",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Добавляйте понравившиеся модели в корзину, чтобы вернуться к ним позже и не искать заново",
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
                favorites = emptySet(),
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
fun CartScreenEmptyPreview() {
    CartScreenContent(
        uiState = CartUiState(
            cartItems = emptyList(),
            recommendedProducts = listOf(
                Product("1", "Товар 1", "123", 1000, description = "", category = "", imageUrl = "")
            )
        ),
        onProductClick = {},
        onGoToCatalog = {},
        onToggleSelectAll = {},
        onToggleItemSelection = { _, _ -> },
        onIncreaseQuantity = { _, _ -> },
        onDecreaseQuantity = { _, _ -> },
        onRemoveFromCart = { _, _ -> },
        onFavoriteToggle = {}
    )
}
