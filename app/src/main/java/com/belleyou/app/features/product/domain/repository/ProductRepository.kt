package com.belleyou.app.features.product.domain.repository

import com.belleyou.app.features.product.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: Int): Product?
}