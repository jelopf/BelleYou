package com.belleyou.core.model

import com.belleyou.core.data.mock.MockImages

fun Product.toUiModel(): ProductUiModel {
    return ProductUiModel(
        product = this,
        images = MockImages.productImages[this.id] ?: emptyList(),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        colors = listOf("Black")
    )
}