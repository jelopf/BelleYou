package com.belleyou.core.repository

import kotlinx.coroutines.flow.Flow

interface CartRepository {

    val cartFlow: Flow<Map<Int, Int>>

    suspend fun add(productId: Int)

    suspend fun remove(productId: Int)

    suspend fun clear()
}