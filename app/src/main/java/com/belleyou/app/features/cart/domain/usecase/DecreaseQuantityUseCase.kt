package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.repository.CartRepository

class DecreaseQuantityUseCase(
    private val repository:
    CartRepository
) {

    operator fun invoke(
        productId: Int
    ) {

        repository
            .decreaseQuantity(productId)
    }
}