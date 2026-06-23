package com.belleyou.feature.product.domain.usecase

import com.belleyou.core.model.Product
import com.belleyou.feature.product.domain.ProductRepository

class GetProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductById(id)
    }
}