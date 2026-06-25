package com.belleyou.core.repository

import com.belleyou.core.assets.ProductJsonDataSource
import com.belleyou.core.model.Product
import com.belleyou.core.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val dataSource: ProductJsonDataSource
) : ProductRepository {

    override fun getProductsFlow(): Flow<List<Product>> = flow {
        emit(
            dataSource.getProducts().map { it.toDomain() }
        )
    }
}