package com.belleyou.core.model

data class Product(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int,
    val oldPrice: Int? = null,
    val description: String,
    val category: String,
    val imageUrl: String,
    val variantImages: List<String>? = null,
    val sizes: List<String> = emptyList()
)