package com.belleyou.core.model

import com.belleyou.core.assets.ProductDto

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        article = article,
        price = price,
        oldPrice = oldPrice,
        rating = rating,
        reviewsCount = reviewsCount,
        brand = brand,
        description = description ?: "",
        modelParameters = "Параметры модели: 175 см, 80/60/90 см. Размер на модели: XS", // Hardcoded for MVP or mapping from DTO if added
        category = category,
        imageUrl = imageUrl,
        variantImages = variantImages ?: emptyList(),
        sizes = sizes ?: emptyList()
    )
}