package com.belleyou.app.features.cart.domain.usecase

import com.belleyou.app.features.cart.domain.repository.CartRepository
import com.belleyou.app.features.product.domain.model.Product

class AddToCartUseCase(
    private val repository: CartRepository
) {

    operator fun invoke(
        product: Product
    ) {
        repository.addToCart(product)
    }
}