package com.belleyou.app.core.data.fake.product

import com.belleyou.app.features.product.domain.model.Product

val fakeProducts = listOf(

    Product(
        id = 1,
        name = "Платье-рубашка",
        article = "BY001",
        price = 5990,
        oldPrice = 7990,
        rating = 4.8f,
        reviewsCount = 145,
        brand = "SELA",
        imageUrl = null,
        colors = listOf("Blue"),
        category = "Платья"
    ),

    Product(
        id = 2,
        name = "Кружевной комплект с огромными яйцами в комплекте", // для проверочки
        article = "BY002",
        price = 4290,
        oldPrice = null,
        rating = 4.9f,
        reviewsCount = 90,
        brand = "CRUISE",
        imageUrl = null,
        colors = listOf("Black", "Red"),
        category = "Комплекты"
    ),

    Product(
        id = 3,
        name = "Базовый топ",
        article = "BY003",
        price = 2490,
        oldPrice = null,
        rating = 4.6f,
        reviewsCount = 101,
        brand = "BELLE YOU",
        imageUrl = null,
        colors = listOf("White", "Beige"),
        category = "Топы"
    ),

    Product(
        id = 4,
        name = "Шелковая пижама",
        article = "BY004",
        price = 7490,
        oldPrice = 9290,
        rating = 4.7f,
        reviewsCount = 54,
        brand = "CRUISE",
        imageUrl = null,
        colors = listOf("Pink"),
        category = "Домашняя одежда"
    ),

    Product(
        id = 5,
        name = "Боди с кружевом",
        article = "BY005",
        price = 3890,
        oldPrice = 4490,
        rating = 4.8f,
        reviewsCount = 68,
        brand = "BELLE YOU",
        imageUrl = null,
        colors = listOf("Black"),
        category = "Боди"
    )
)