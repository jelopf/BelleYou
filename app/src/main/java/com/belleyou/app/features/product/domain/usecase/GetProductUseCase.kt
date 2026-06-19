package com.belleyou.app.features.product.domain.usecase

import com.belleyou.app.features.product.domain.model.Product
import com.belleyou.app.features.product.domain.repository.ProductRepository

class GetProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductById(id)
    }
}