package com.belleyou.core.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    val favoritesFlow: Flow<Set<Int>>

    suspend fun toggleFavorite(productId: Int)

    suspend fun isFavorite(productId: Int): Boolean
}