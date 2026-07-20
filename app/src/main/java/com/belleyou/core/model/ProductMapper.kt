package com.belleyou.core.model

import com.belleyou.core.assets.ProductDto

fun ProductDto.toDomain(): Product {
    // Generate a unique ID using category and original ID from JSON
    val uniqueId = "${category.lowercase().replace(" ", "_")}_$id"
    
    return Product(
        id = uniqueId,
        name = name,
        article = article,
        price = price,
        oldPrice = oldPrice,
        rating = rating,
        reviewsCount = reviewsCount,
        brand = brand,
        description = description ?: "",
        modelParameters = modelParameters ?: "Параметры модели: 175 см, 80/60/90 см. Размер на модели: XS",
        category = category,
        imageUrl = imageUrl,
        variantImages = variantImages ?: emptyList(),
        sizes = sizes ?: emptyList()
    )
}