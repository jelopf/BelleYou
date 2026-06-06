package com.belleyou.app.core.data.fake.product

import com.belleyou.app.features.product.domain.model.Product

val fakeProducts = listOf(

    Product(
        id = 1,
        name = "Платье-рубашка",
        article = "BY001",
        price = 5990,
        rating = 4.8f,
        imageUrl = null,
        colors = listOf("Blue"),
        category = "Платья"
    ),

    Product(
        id = 2,
        name = "Кружевной комплект",
        article = "BY002",
        price = 4290,
        rating = 4.9f,
        imageUrl = null,
        colors = listOf("Black", "Red"),
        category = "Комплекты"
    ),

    Product(
        id = 3,
        name = "Базовый топ",
        article = "BY003",
        price = 2490,
        rating = 4.6f,
        imageUrl = null,
        colors = listOf("White", "Beige"),
        category = "Топы"
    ),

    Product(
        id = 4,
        name = "Шелковая пижама",
        article = "BY004",
        price = 7490,
        rating = 4.7f,
        imageUrl = null,
        colors = listOf("Pink"),
        category = "Домашняя одежда"
    ),

    Product(
        id = 5,
        name = "Боди с кружевом",
        article = "BY005",
        price = 3890,
        rating = 4.8f,
        imageUrl = null,
        colors = listOf("Black"),
        category = "Боди"
    )
)