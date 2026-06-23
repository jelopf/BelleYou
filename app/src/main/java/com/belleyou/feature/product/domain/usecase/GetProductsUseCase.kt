package com.belleyou.feature.product.domain.usecase

import com.belleyou.core.model.Product
import com.belleyou.feature.product.domain.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}