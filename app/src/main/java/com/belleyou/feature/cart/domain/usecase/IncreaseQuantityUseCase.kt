package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository

class IncreaseQuantityUseCase(
    private val repository:
    CartRepository
) {

    operator fun invoke(
        productId: Int
    ) {

        repository
            .increaseQuantity(productId)
    }
}