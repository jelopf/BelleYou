package com.belleyou.feature.recommendations.ui

import com.belleyou.core.model.Product

data class RecommendationsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val currentIndex: Int = 0
)