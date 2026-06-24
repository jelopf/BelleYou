package com.belleyou.feature.product.ui

import com.belleyou.core.model.ProductUiModel

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: ProductUiModel? = null,
    val error: String? = null,
    val selectedSize: String = "",
    val isFavorite: Boolean = false,
    val showDescription: Boolean = false
)