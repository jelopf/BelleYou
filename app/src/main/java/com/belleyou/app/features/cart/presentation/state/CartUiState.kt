package com.belleyou.app.features.cart.presentation.state

import com.belleyou.app.features.cart.domain.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Int = 0
)