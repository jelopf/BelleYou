package com.belleyou.core.designsystem.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.belleyou.core.model.Product

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    showSizeSelector: Boolean = false,
    showInCartButton: Boolean = false,
    showFavoriteIcon: Boolean = false,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onAddToCartClick: (String?) -> Unit = {},
) {

    var selectedSize by remember(product.id) {
        mutableStateOf<String?>(null)
    }

    var sizeExpanded by remember(product.id) {
        mutableStateOf(false)
    }

    ProductCardContent(
        modifier = modifier,
        product = product,
        isFavorite = isFavorite,
        selectedSize = selectedSize,
        sizes = product.sizes ?: emptyList(),
        showFavoriteIcon = showFavoriteIcon,
        showSizeSelector = showSizeSelector,
        showInCartButton = showInCartButton,

        expanded = sizeExpanded,
        onExpandedChange = { sizeExpanded = it },

        onSizeSelected = {
            selectedSize = it
        },

        onClick = onClick,
        onFavoriteClick = onFavoriteClick,
        onAddToCartClick = {
            onAddToCartClick(selectedSize)
        }
    )
}