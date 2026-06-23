package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository

class RemoveFromCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke(
        productId: Int
    ) {
        repository.removeFromCart(productId)
    }
}