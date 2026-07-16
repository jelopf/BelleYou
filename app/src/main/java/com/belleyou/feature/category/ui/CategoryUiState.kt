package com.belleyou.feature.category.ui

import com.belleyou.core.model.Product

data class CategoryUiState(
    val products: List<Product> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val isSortOverlayVisible: Boolean = false,
    val selectedSortOption: SortOption = SortOption.NONE
)

enum class SortOption {
    NONE,
    NEWEST,
    PRICE_ASC,
    PRICE_DESC
}