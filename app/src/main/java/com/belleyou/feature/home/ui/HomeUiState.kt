package com.belleyou.feature.home.ui

import com.belleyou.core.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)