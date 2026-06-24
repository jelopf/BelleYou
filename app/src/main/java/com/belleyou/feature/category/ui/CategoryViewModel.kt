package com.belleyou.feature.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.toUiModel
import com.belleyou.feature.product.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    fun load(categoryName: String) {
        viewModelScope.launch {
            val products = getProductsUseCase()
                .filter { it.category == categoryName }
                .map { it.toUiModel() }

            _uiState.value = CategoryUiState(
                products = products
            )
        }
    }

    fun toggleFavorite(productId: Int) {

        val favorites =
            _uiState.value
                .favorites
                .toMutableSet()

        if (!favorites.add(productId)) {
            favorites.remove(productId)
        }

        _uiState.update {
            it.copy(
                favorites = favorites
            )
        }
    }
}