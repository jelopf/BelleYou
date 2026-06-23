package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository

class ClearCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke() {
        repository.clearCart()
    }
}