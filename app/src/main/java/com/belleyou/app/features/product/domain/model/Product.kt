package com.belleyou.app.features.product.domain.model

data class Product(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int,
    val rating: Float,
    val imageUrl: String? = null,
    val colors: List<String>,
    val category: String,
    val isFavorite: Boolean = false
)