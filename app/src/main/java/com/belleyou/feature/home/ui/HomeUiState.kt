package com.belleyou.feature.home.ui

import com.belleyou.core.model.ProductUiModel

data class HomeUiState(
    val products: List<ProductUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)