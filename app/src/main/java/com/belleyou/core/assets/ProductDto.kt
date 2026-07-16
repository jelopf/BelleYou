package com.belleyou.core.assets

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int? = null,
    val oldPrice: Int? = null,
    val rating: Double? = null,
    val reviewsCount: Int? = null,
    val brand: String? = null,
    val imageUrl: String,
    val description: String? = null,
    val variantImages: List<String>? = null,
    val sizes: List<String>? = null,
    val category: String
)