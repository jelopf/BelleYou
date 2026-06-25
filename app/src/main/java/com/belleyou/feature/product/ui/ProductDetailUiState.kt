package com.belleyou.feature.product.ui

import com.belleyou.core.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null,
    val selectedSize: String = "",
    val isFavorite: Boolean = false,
    val showDescription: Boolean = false
)