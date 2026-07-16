package com.belleyou.feature.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.Product
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.recommendations.ui.RecommendationsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecommendationsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeData()
        observeFavorites()
    }

    private fun observeData() {
        viewModelScope.launch {
            productRepository.getProductsFlow()
                .collect { products ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            products = products
                        )
                    }
                }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesRepository.favoritesFlow.collect { favs ->
                _uiState.update {
                    it.copy(favorites = favs)
                }
            }
        }
    }

    fun dismissOnboarding() {
        _uiState.update { it.copy(showOnboarding = false) }
    }

    fun nextProduct() {
        _uiState.update { state ->
            if (state.products.isEmpty()) return@update state

            state.copy(
                currentIndex = (state.currentIndex + 1) % state.products.size
            )
        }
    }

    fun currentProduct(): Product? =
        _uiState.value.products.getOrNull(_uiState.value.currentIndex)

    fun addToCart() {
        currentProduct()?.let { product ->
            viewModelScope.launch {
                cartRepository.add(
                    product.id,
                    product.sizes.firstOrNull().orEmpty()
                )
            }
        }
    }

    fun toggleFavorite() {
        currentProduct()?.let { product ->
            viewModelScope.launch {
                favoritesRepository.toggleFavorite(product.id)
            }
        }
    }
}