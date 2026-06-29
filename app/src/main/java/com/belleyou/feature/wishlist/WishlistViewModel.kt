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

    private val _uiState = MutableStateFlow(
        WishlistUiState(
            wishlists = listOf(
                "избранное ❤️",
                "на пляж 🌴",
                "на каждый день ☀️"
            )
        )
    )

    val uiState = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {

            combine(
                productRepository.getProductsFlow(),
                favoritesRepository.favoritesFlow
            ) { products, favorites ->

                val filtered = products.filter { it.id in favorites }

                WishlistUiState(
                    wishlists = _uiState.value.wishlists,
                    selectedWishlistIndex = _uiState.value.selectedWishlistIndex,
                    products = filtered,
                    favorites = favorites
                )

            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun selectWishlist(index: Int) {
        _uiState.update {
            it.copy(selectedWishlistIndex = index)
        }
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }

    fun addToCart(productId: Int) {
        val product = _uiState.value.products.find { it.id == productId } ?: return

        viewModelScope.launch {
            cartRepository.add(
                product.id,
                product.sizes.firstOrNull().orEmpty()
            )
        }
    }
}