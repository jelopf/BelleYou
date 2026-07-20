package com.belleyou.core.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    val favoritesFlow: Flow<Set<String>>

    suspend fun toggleFavorite(productId: String)

    suspend fun isFavorite(productId: String): Boolean
}