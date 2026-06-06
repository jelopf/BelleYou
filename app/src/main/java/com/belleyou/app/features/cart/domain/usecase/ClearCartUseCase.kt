package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.repository.CartRepository

class ClearCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke() {
        repository.clearCart()
    }
}