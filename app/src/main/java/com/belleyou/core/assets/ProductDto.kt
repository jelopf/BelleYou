package com.belleyou.core.assets

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int,
    val oldPrice: Int? = null,
    val description: String,
    val category: String,
    val imageUrl: String,
    val variantImages: List<String>? = null,
    val sizes: List<String>? = null
)