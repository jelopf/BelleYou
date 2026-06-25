package com.belleyou.core.assets

import android.content.Context
import kotlinx.serialization.json.Json

class ProductJsonDataSource(
    private val context: Context
) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun getProducts(): List<ProductDto> {
        val jsonString = context.assets
            .open("products.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonString)
    }
}