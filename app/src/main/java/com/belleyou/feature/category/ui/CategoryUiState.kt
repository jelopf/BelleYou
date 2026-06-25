package com.belleyou.feature.category.ui

import com.belleyou.core.model.Product

data class CategoryUiState(
    val products: List<Product> = emptyList(),
    val favorites: Set<Int> = emptySet()
)