package com.belleyou.core.model

import com.belleyou.core.assets.ProductDto

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        article = article,
        price = price,
        oldPrice = oldPrice,
        description = description,
        category = category,
        imageUrl = imageUrl,
        variantImages = variantImages ?: emptyList(),
        sizes = sizes ?: emptyList()
    )
}