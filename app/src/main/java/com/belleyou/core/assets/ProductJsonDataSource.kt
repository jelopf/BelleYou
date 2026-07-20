package com.belleyou.core.assets

import android.content.Context
import kotlinx.serialization.json.Json
import android.util.Log

class ProductJsonDataSource(
    private val context: Context
) {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private var cachedProducts: List<ProductDto>? = null

    fun getProducts(): List<ProductDto> {
        cachedProducts?.let { return it }

        val products = mutableListOf<ProductDto>()
        try {
            // Список всех файлов в assets
            val files = context.assets.list("") ?: emptyArray()

            // Фильтруем только .json файлы (исключая системные, если есть)
            val jsonFiles = files.filter { it.endsWith(".json") && it != "webkit" }

            jsonFiles.forEach { fileName ->
                try {
                    val jsonString = context.assets
                        .open(fileName)
                        .bufferedReader()
                        .use { it.readText() }
                    
                    val items = json.decodeFromString<List<ProductDto>>(jsonString)
                    
                    // Добавляем префикс к ID, чтобы избежать дубликатов между файлами
                    // Используем имя файла как префикс категории
                    val categoryPrefix = fileName.removeSuffix(".json")
                    val uniqueItems = items.map { item ->
                        item.copy(category = categoryPrefix)
                    }
                    
                    products.addAll(uniqueItems)
                    Log.d("ProductDataSource", "Loaded ${items.size} products from $fileName")
                } catch (e: Exception) {
                    Log.e("ProductDataSource", "Error loading $fileName", e)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductDataSource", "Error listing assets", e)
        }

        return products.also { cachedProducts = it }
    }
}