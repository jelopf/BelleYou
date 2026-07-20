package com.belleyou.core.repository

import com.belleyou.core.assets.ProductJsonDataSource
import com.belleyou.core.model.Product
import com.belleyou.core.model.toDomain
import com.belleyou.core.util.ColorUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val dataSource: ProductJsonDataSource
) : ProductRepository {

    override fun getProductsFlow(): Flow<List<Product>> = flow {
        val domainProducts = dataSource.getProducts().map { it.toDomain() }
        
        // Enrich products with color variants
        val enriched = domainProducts.map { product ->
            val variants = domainProducts.filter { it.name == product.name }
            val hexes = variants.map { 
                ColorUtils.getColorHex(ColorUtils.extractColorName(it.article)) 
            }.distinct()
            
            product.copy(colorHexes = hexes)
        }
        
        emit(enriched)
    }
}