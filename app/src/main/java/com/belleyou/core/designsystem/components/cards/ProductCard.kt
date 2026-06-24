package com.belleyou.core.designsystem.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.belleyou.core.model.ProductUiModel

@Composable
fun ProductCard(
    uiModel: ProductUiModel,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    showSizeSelector: Boolean = false,
    showInCartButton: Boolean = false,
    showFavoriteIcon: Boolean = false,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onAddToCartClick: (String?) -> Unit = {},
) {

    var selectedSize by remember(uiModel.product.id) {
        mutableStateOf<String?>(null)
    }

    var sizeExpanded by remember(uiModel.product.id) {
        mutableStateOf(false)
    }

    ProductCardContent(
        modifier = modifier,
        product = uiModel,
        isFavorite = isFavorite,
        selectedSize = selectedSize,
        sizes = uiModel.sizes,
        showFavoriteIcon = showFavoriteIcon,
        showSizeSelector = showSizeSelector,
        showInCartButton = showInCartButton,

        onClick = onClick,
        onFavoriteClick = onFavoriteClick,

        expanded = sizeExpanded,
        onExpandedChange = { sizeExpanded = it },

        onSizeSelected = {
            selectedSize = it
        },

        onAddToCartClick = {
            onAddToCartClick(selectedSize)
        }
    )
}