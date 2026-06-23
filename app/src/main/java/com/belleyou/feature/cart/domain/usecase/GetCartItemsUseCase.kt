package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository

class GetCartItemsUseCase(
    private val repository: CartRepository
) {

    operator fun invoke() =
        repository.getCartItems()
}