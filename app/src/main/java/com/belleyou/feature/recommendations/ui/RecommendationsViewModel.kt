package com.belleyou.feature.recommendations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.toUiModel
import com.belleyou.feature.product.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecommendationsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val products = getProductsUseCase()
                .map { it.toUiModel() }

            _uiState.value = RecommendationsUiState(
                isLoading = false,
                products = products
            )
        }
    }
}