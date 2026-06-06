package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.model.CartItem

class CalculateCartTotalUseCase {

    operator fun invoke(
        cartItems: List<CartItem>
    ): Int {

        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }
}