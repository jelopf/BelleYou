package com.belleyou.core.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.belleyou.feature.cart.domain.CartKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject

private val Context.cartDataStore by preferencesDataStore("cart")

class CartRepositoryImpl(
    private val context: Context
) : CartRepository {

    private val CART_KEY = stringPreferencesKey("cart_json")

    override val cartFlow: Flow<Map<CartKey, Int>> =
        context.cartDataStore.data.map { prefs ->
            val jsonString = prefs[CART_KEY] ?: return@map emptyMap()

            val json = JSONObject(jsonString)
            val result = mutableMapOf<CartKey, Int>()

            json.keys().forEach { key ->
                val parts = key.split(":")

                val productId = parts.getOrNull(0)?.toIntOrNull() ?: return@forEach
                val size = parts.getOrNull(1) ?: "" // защита от старых данных

                result[CartKey(productId, size)] = json.getInt(key)
            }

            result
        }

    override suspend fun add(productId: Int, selectedSize: String) {
        context.cartDataStore.edit { prefs ->
            val current = prefs[CART_KEY]?.let {
                JSONObject(it)
            } ?: JSONObject()

            val key = "$productId:$selectedSize"
            val newValue = current.optInt(key, 0) + 1

            current.put(key, newValue)

            prefs[CART_KEY] = current.toString()
        }
    }

    override suspend fun remove(productId: Int, selectedSize: String) {
        context.cartDataStore.edit { prefs ->
            val current = prefs[CART_KEY]?.let {
                JSONObject(it)
            } ?: JSONObject()

            val key = "$productId:$selectedSize"
            val currentCount = current.optInt(key, 0)

            if (currentCount <= 1) {
                current.remove(key)
            } else {
                current.put(key, currentCount - 1)
            }

            prefs[CART_KEY] = current.toString()
        }
    }

    override suspend fun removeAll(productId: Int, selectedSize: String) {
        context.cartDataStore.edit { prefs ->
            val current = prefs[CART_KEY]?.let {
                JSONObject(it)
            } ?: JSONObject()

            val key = "$productId:$selectedSize"

            current.remove(key)

            prefs[CART_KEY] = current.toString()
        }
    }
}