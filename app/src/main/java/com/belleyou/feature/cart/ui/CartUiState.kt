package com.belleyou.feature.cart.ui

import com.belleyou.feature.cart.domain.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Int = 0,
    val selectedSize: String = ""
)