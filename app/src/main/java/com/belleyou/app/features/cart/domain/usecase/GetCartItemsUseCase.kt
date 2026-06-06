package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.repository.CartRepository

class GetCartItemsUseCase(
    private val repository: CartRepository
) {

    operator fun invoke() =
        repository.getCartItems()
}