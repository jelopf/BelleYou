package com.belleyou.feature.wishlist.ui

import androidx.lifecycle.ViewModel
import com.belleyou.core.data.mock.fakeProducts
import com.belleyou.core.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WishlistViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        WishlistUiState(
            wishlists = listOf("избранное ❤️", "на пляж 🌴", "на каждый день ☀️"),
            products = fakeProducts.map { it.toUiModel() }
        )
    )

    val uiState = _uiState.asStateFlow()

    fun selectWishlist(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedWishlistIndex = index
        )
    }

    fun toggleFavorite(productId: Int) {
        val current = _uiState.value.favorites.toMutableSet()

        if (!current.add(productId)) {
            current.remove(productId)
        }

        _uiState.value = _uiState.value.copy(
            favorites = current
        )
    }
}