package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.repository.CartRepository

class RemoveFromCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke(
        productId: Int
    ) {
        repository.removeFromCart(productId)
    }
}