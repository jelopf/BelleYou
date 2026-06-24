package com.belleyou.feature.category.ui

import com.belleyou.core.model.ProductUiModel

data class CategoryUiState(
    val products: List<ProductUiModel> = emptyList(),
    val favorites: Set<Int> = emptySet()
)