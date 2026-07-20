package com.belleyou.feature.cart.ui

import com.belleyou.core.model.Product
import com.belleyou.feature.cart.domain.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val selectedItems: Set<Pair<String, String>> = emptySet(),
    val totalPrice: Int = 0,
    val recommendedProducts: List<Product> = emptyList(),
    val favorites: Set<String> = emptySet()
)
