package com.belleyou.app.features.product.domain.usecase

import com.belleyou.app.features.product.domain.model.Product
import com.belleyou.app.features.product.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}