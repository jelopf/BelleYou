package com.belleyou.core.repository

import com.belleyou.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsFlow(): Flow<List<Product>>
}