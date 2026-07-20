package com.belleyou.core.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    val favoritesFlow: Flow<Set<String>>
    val wishlistsFlow: Flow<Map<String, Set<String>>>

    suspend fun toggleFavorite(productId: String, wishlistName: String = "ИЗБРАННОЕ")
    
    suspend fun createWishlist(name: String)
    
    suspend fun deleteWishlist(name: String)
    
    suspend fun moveProduct(productId: String, fromWishlist: String, toWishlist: String)

    suspend fun isFavorite(productId: String): Boolean
}