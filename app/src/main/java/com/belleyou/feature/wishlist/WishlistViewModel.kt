package com.belleyou.feature.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.wishlist.ui.WishlistUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val selectedWishlistName = MutableStateFlow("ИЗБРАННОЕ")

    init {
        viewModelScope.launch {
            combine(
                productRepository.getProductsFlow(),
                favoritesRepository.wishlistsFlow,
                favoritesRepository.favoritesFlow,
                selectedWishlistName
            ) { allProducts, wishlistsMap, allFavorites, selectedName ->
                
                val productIds = wishlistsMap[selectedName] ?: emptySet()
                val filteredProducts = allProducts.filter { it.id in productIds }

                WishlistUiState(
                    wishlists = wishlistsMap.keys.toList(),
                    selectedWishlistName = selectedName,
                    products = filteredProducts,
                    recommendedProducts = allProducts.filter { it.id !in allFavorites }.shuffled().take(6),
                    favorites = allFavorites,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun selectWishlist(name: String) {
        selectedWishlistName.value = name
    }

    fun createWishlist(name: String) {
        viewModelScope.launch {
            favoritesRepository.createWishlist(name)
        }
    }

    fun deleteWishlist(name: String) {
        viewModelScope.launch {
            if (selectedWishlistName.value == name) {
                selectedWishlistName.value = "ИЗБРАННОЕ"
            }
            favoritesRepository.deleteWishlist(name)
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId, selectedWishlistName.value)
        }
    }

    fun moveProduct(productId: String, targetWishlist: String) {
        viewModelScope.launch {
            favoritesRepository.moveProduct(productId, selectedWishlistName.value, targetWishlist)
        }
    }

    fun addToCart(productId: String) {
        val product = _uiState.value.products.find { it.id == productId } ?: return
        viewModelScope.launch {
            cartRepository.add(product.id, product.sizes.firstOrNull().orEmpty())
        }
    }
    
    fun getShareText(): String {
        val state = _uiState.value
        val productsText = state.products.joinToString("\n") { "- ${it.name}: ${it.price} ₽" }
        return "Мой вишлист '${state.selectedWishlistName}' в Belle You:\n\n$productsText"
    }
}