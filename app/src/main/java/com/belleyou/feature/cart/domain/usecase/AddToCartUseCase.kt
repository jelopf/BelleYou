package com.belleyou.feature.cart.domain.usecase

import com.belleyou.feature.cart.domain.CartRepository
import com.belleyou.core.model.Product

class AddToCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke(
        product: Product
    ) {
        repository.addToCart(product)
    }
}