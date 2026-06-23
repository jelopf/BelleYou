package com.belleyou.feature.product.data

import com.belleyou.core.data.fake.fakeProducts
import com.belleyou.core.model.Product
import com.belleyou.feature.product.domain.ProductRepository

class ProductRepositoryImpl : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return fakeProducts
    }

    override suspend fun getProductById(id: Int): Product? {
        return fakeProducts.find { it.id == id }
    }
}