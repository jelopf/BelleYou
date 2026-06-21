package com.belleyou.app.core.data.fake.product

import com.belleyou.app.features.product.domain.model.Product
import com.belleyou.app.R

val fakeProducts = listOf(

    Product(
        id = 1,
        name = "Лонгслив из хлопка Одежда для отдыха / Cruise черно-молочная полоска",
        article = "BY001",
        price = 5990,
        oldPrice = 7990,
        rating = 4.8f,
        reviewsCount = 145,
        brand = "SELA",
        imageUrl = null,
        description = "Лонгслив из мягкого хлопка в рубчик — базовая вещь для вашего гардероба. Модель с глубоким круглым вырезом и длинными рукавами. Идеально подходит для создания многослойных образов или как самостоятельный элемент.\n\n• Облегающий крой\n• Мягкий трикотаж в рубчик\n• Глубокий круглый вырез\n• Рост модели 175 см, на ней размер S",
        colors = listOf("Blue"),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        variantImages = listOf(R.drawable.slide, R.drawable.slide),
        category = "Одежда"
    ),

    Product(
        id = 2,
        name = "Кружевной комплект",
        article = "BY002",
        price = 4290,
        oldPrice = null,
        rating = 4.9f,
        reviewsCount = 90,
        brand = "CRUISE",
        imageUrl = null,
        description = "Элегантный кружевной комплект, выполненный из утонченных материалов. Идеальное сочетание комфорта и смелого дизайна для особых случаев.",
        colors = listOf("Black", "Red"),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        variantImages = listOf(R.drawable.slide, R.drawable.slide),
        category = "Нижнее белье"
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
        description = "Универсальный базовый топ из приятного к телу трикотажа. Незаменимая вещь в повседневном гардеробе, отлично сочетается с джинсами, пиджаками и кардиганами.",
        colors = listOf("White", "Beige"),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        variantImages = listOf(R.drawable.slide, R.drawable.slide),
        category = "Одежда"
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
        description = "Роскошная шелковая пижама для идеального домашнего отдыха. Свободный крой не сковывает движения, а нежный материал дарит ощущение абсолютного уюта.",
        colors = listOf("Pink"),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        variantImages = listOf(R.drawable.slide, R.drawable.slide),
        category = "Одежда"
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
        description = "Изящное боди с деликатными кружевными вставками. Модель отлично садится по фигуре, подчеркивая силуэт. Подходит как для создания вечерних образов, так и под одежду.",
        colors = listOf("Black"),
        sizes = listOf("XS", "S", "M", "L", "XL"),
        variantImages = listOf(R.drawable.slide, R.drawable.slide),
        category = "Одежда"
    )
)