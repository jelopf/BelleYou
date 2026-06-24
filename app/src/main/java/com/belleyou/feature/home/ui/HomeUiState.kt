package com.belleyou.feature.home.ui

import com.belleyou.core.model.ProductUiModel

data class HomeUiState(
    val products: List<ProductUiModel> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)