package com.belleyou.feature.product.domain

import com.belleyou.core.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: Int): Product?
}