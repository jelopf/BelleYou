package com.belleyou.app.features.product.domain.data.repository

import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.features.product.domain.repository.ProductRepository
import com.belleyou.app.features.product.domain.model.Product

class ProductRepositoryImpl : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return fakeProducts
    }

    override suspend fun getProductById(id: Int): Product? {
        return fakeProducts.find { it.id == id }
    }
}