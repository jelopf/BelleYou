package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartItem

class CalculateCartTotalUseCase {

    operator fun invoke(
        cartItems: List<CartItem>
    ): Int {

        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }
}