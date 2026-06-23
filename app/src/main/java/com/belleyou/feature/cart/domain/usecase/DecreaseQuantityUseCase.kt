package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository

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