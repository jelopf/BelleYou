package com.belleyou.core.model

data class Product(
    val id: String,
    val name: String,
    val article: String,
    val price: Int?,
    val oldPrice: Int? = null,
    val rating: Double? = null,
    val reviewsCount: Int? = null,
    val brand: String? = null,
    val description: String,
    val modelParameters: String? = null,
    val category: String,
    val imageUrl: String,
    val variantImages: List<String> = emptyList(),
    val sizes: List<String> = emptyList(),
    val colorHexes: List<String> = emptyList()
)