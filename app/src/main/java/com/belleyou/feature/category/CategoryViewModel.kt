package com.belleyou.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.Product
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.category.ui.CategoryUiState
import com.belleyou.feature.category.ui.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    private var currentCategoryName: String = ""

    fun load(categoryName: String) {
        currentCategoryName = categoryName
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                productRepository.getProductsFlow(),
                favoritesRepository.favoritesFlow
            ) { products, favorites ->
                val filteredProducts = when (categoryName) {
                    "new" -> products.shuffled().take(20)
                    "trending" -> products.reversed().take(20)
                    "recommended" -> products.shuffled()
                    else -> products.filter { it.category == categoryName }
                }
                
                val sortedProducts = sortProducts(filteredProducts, _uiState.value.selectedSortOption)
                
                CategoryUiState(
                    products = sortedProducts,
                    favorites = favorites,
                    isLoading = false,
                    error = null,
                    isSortOverlayVisible = _uiState.value.isSortOverlayVisible,
                    selectedSortOption = _uiState.value.selectedSortOption
                )
            }.catch { e ->
                _uiState.update { it.copy(isLoading = false, error = "Ошибка: ${e.message}") }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun setSortOption(option: SortOption) {
        _uiState.update { it.copy(selectedSortOption = option, isSortOverlayVisible = false) }
        load(currentCategoryName)
    }

    fun toggleSortOverlay(visible: Boolean) {
        _uiState.update { it.copy(isSortOverlayVisible = visible) }
    }

    private fun sortProducts(products: List<Product>, option: SortOption): List<Product> {
        return when (option) {
            SortOption.NONE -> products
            SortOption.NEWEST -> products.reversed()
            SortOption.PRICE_ASC -> products.sortedBy { it.price ?: 0 }
            SortOption.PRICE_DESC -> products.sortedByDescending { it.price ?: 0 }
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }
}
