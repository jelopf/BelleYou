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
    val colors: List<String>,
    val category: String,
    val isFavorite: Boolean = false,
    val availableSizes: List<String>
)