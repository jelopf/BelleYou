package com.belleyou.app.features.cart.domain.repository

import com.belleyou.app.features.cart.domain.model.CartItem
import com.belleyou.app.features.product.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {

    fun getCartItems(): StateFlow<List<CartItem>>

    fun addToCart(product: Product)

    fun removeFromCart(productId: Int)

    fun increaseQuantity(productId: Int)

    fun decreaseQuantity(productId: Int)

    fun clearCart()
}