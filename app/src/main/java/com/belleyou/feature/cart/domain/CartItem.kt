package com.belleyou.feature.cart.domain

import com.belleyou.core.model.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
    val selectedSize: String
)