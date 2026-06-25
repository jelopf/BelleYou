package com.belleyou.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.home.ui.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
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

                HomeUiState(
                    products = products,
                    favorites = favorites,
                    searchQuery = _uiState.value.searchQuery,
                    isLoading = false
                )

            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }
}