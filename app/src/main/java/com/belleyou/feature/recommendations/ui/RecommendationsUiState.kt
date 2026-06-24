package com.belleyou.feature.recommendations.ui

import com.belleyou.core.model.ProductUiModel

data class RecommendationsUiState(
    val isLoading: Boolean = false,
    val products: List<ProductUiModel> = emptyList()
)