package com.belleyou.feature.cart.domain

import com.belleyou.core.model.Product
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {

    fun getCartItems(): StateFlow<List<CartItem>>

    fun addToCart(product: Product)

    fun removeFromCart(productId: Int)

    fun increaseQuantity(productId: Int)

    fun decreaseQuantity(productId: Int)

    fun clearCart()
}