package com.belleyou.feature.wishlist.ui

import com.belleyou.core.model.ProductUiModel

data class WishlistUiState(
    val wishlists: List<String> = emptyList(),
    val selectedWishlistIndex: Int = 0,
    val products: List<ProductUiModel> = emptyList(),
    val favorites: Set<Int> = emptySet()
)