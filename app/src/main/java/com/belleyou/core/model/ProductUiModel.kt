package com.belleyou.core.model

data class ProductUiModel(
    val product: Product,
    val images: List<Int>,
    val sizes: List<String>,
    val colors: List<String>
)