package com.belleyou.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.category.ui.CategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun load(categoryName: String) {
        viewModelScope.launch {

            combine(
                productRepository.getProductsFlow(),
                favoritesRepository.favoritesFlow
            ) { products, favorites ->

                val filteredProducts = products
                    .filter { it.category == categoryName }

                CategoryUiState(
                    products = filteredProducts,
                    favorites = favorites
                )

            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }
}