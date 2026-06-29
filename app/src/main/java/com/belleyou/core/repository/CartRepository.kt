package com.belleyou.core.repository

import com.belleyou.feature.cart.domain.CartKey
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    val cartFlow: Flow<Map<CartKey, Int>>

    suspend fun add(productId: Int, selectedSize: String)

    suspend fun remove(productId: Int, selectedSize: String)

    suspend fun removeAll(productId: Int, selectedSize: String)
}