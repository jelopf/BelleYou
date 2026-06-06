package com.belleyou.app.features.cart.domain.model

import com.belleyou.app.features.product.domain.model.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)