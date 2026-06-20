package com.belleyou.app.features.product.domain.model

data class Product(
    val id: Int,
    val name: String,
    val article: String,
    val price: Int,
    val oldPrice: Int? = null, // старая цена для скидки
    val rating: Float,
    val reviewsCount: Int = 0,  // количество отзывов
    val brand: String,          // бренд
    val imageUrl: String? = null,
    val description: String = "",
    val colors: List<String>,
    val sizes: List<String> = emptyList(),
    val variantImages: List<Int> = emptyList(),
    val category: String,
    val isFavorite: Boolean = false
)