package com.belleyou.feature.wishlist.ui

import com.belleyou.core.model.Product

data class WishlistUiState(
    val wishlists: List<String> = emptyList(),
    val selectedWishlistIndex: Int = 0,
    val products: List<Product> = emptyList(),
    val recommendedProducts: List<Product> = emptyList(),
    val favorites: Set<String> = emptySet()
)
