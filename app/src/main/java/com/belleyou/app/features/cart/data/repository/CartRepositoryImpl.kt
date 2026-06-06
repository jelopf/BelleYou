package com.belleyou.app.features.cart.data.repository

import com.belleyou.app.features.cart.domain.model.CartItem
import com.belleyou.app.features.cart.domain.repository.CartRepository
import com.belleyou.app.features.product.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl : CartRepository {

    private val _cartItems =
        MutableStateFlow<List<CartItem>>(emptyList())

    override fun getCartItems():
            StateFlow<List<CartItem>> {

        return _cartItems.asStateFlow()
    }

    override fun addToCart(
        product: Product
    ) {

        val currentItems =
            _cartItems.value.toMutableList()

        val existingItemIndex =
            currentItems.indexOfFirst {
                it.product.id == product.id
            }

        if (existingItemIndex != -1) {

            val existingItem =
                currentItems[existingItemIndex]

            currentItems[existingItemIndex] =
                existingItem.copy(
                    quantity =
                        existingItem.quantity + 1
                )

        } else {

            currentItems.add(
                CartItem(product = product)
            )
        }

        _cartItems.value = currentItems
    }

    override fun removeFromCart(
        productId: Int
    ) {

        _cartItems.value =
            _cartItems.value.filter {
                it.product.id != productId
            }
    }

    override fun increaseQuantity(
        productId: Int
    ) {

        _cartItems.value =
            _cartItems.value.map { item ->

                if (
                    item.product.id == productId
                ) {

                    item.copy(
                        quantity =
                            item.quantity + 1
                    )

                } else {
                    item
                }
            }
    }

    override fun decreaseQuantity(
        productId: Int
    ) {

        _cartItems.value =
            _cartItems.value.mapNotNull { item ->

                if (
                    item.product.id == productId
                ) {

                    when {

                        item.quantity > 1 -> {
                            item.copy(
                                quantity =
                                    item.quantity - 1
                            )
                        }

                        else -> null
                    }

                } else {
                    item
                }
            }
    }

    override fun clearCart() {
        _cartItems.value = emptyList()
    }
}