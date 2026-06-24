package com.belleyou.core.model

fun Product.toUiModel(): ProductUiModel {
    return ProductUiModel(
        product = this,
        images = emptyList(),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        colors = listOf("Black")
    )
}