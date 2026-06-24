package com.belleyou.core.model

data class Product(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int,
    val oldPrice: Int? = null,
    val rating: Float,
    val reviewsCount: Int,
    val brand: String,
    val description: String,
    val category: String
)