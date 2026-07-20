package com.belleyou.core.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.favoritesDataStore by preferencesDataStore("favorites_v2")

class FavoritesRepositoryImpl(
    private val context: Context
) : FavoritesRepository {

    private val WISHLISTS_KEY = stringPreferencesKey("wishlists_json")

    override val wishlistsFlow: Flow<Map<String, Set<String>>> =
        context.favoritesDataStore.data.map { prefs ->
            val jsonString = prefs[WISHLISTS_KEY] ?: return@map mapOf("ИЗБРАННОЕ" to emptySet())
            
            val result = mutableMapOf<String, Set<String>>()
            val json = JSONObject(jsonString)
            json.keys().forEach { name ->
                val ids = mutableSetOf<String>()
                val array = json.getJSONArray(name)
                for (i in 0 until array.length()) {
                    ids.add(array.getString(i))
                }
                result[name] = ids
            }
            if (!result.containsKey("ИЗБРАННОЕ")) {
                result["ИЗБРАННОЕ"] = emptySet()
            }
            result
        }

    override val favoritesFlow: Flow<Set<String>> =
        wishlistsFlow.map { it.values.flatten().toSet() }

    override suspend fun toggleFavorite(productId: String, wishlistName: String) {
        context.favoritesDataStore.edit { prefs ->
            val jsonString = prefs[WISHLISTS_KEY] ?: "{ \"ИЗБРАННОЕ\": [] }"
            val json = JSONObject(jsonString)
            
            val array = if (json.has(wishlistName)) {
                json.getJSONArray(wishlistName)
            } else {
                JSONArray().also { json.put(wishlistName, it) }
            }
            
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                list.add(array.getString(i))
            }
            
            if (list.contains(productId)) {
                list.remove(productId)
            } else {
                list.add(productId)
            }
            
            json.put(wishlistName, JSONArray(list))
            prefs[WISHLISTS_KEY] = json.toString()
        }
    }

    override suspend fun createWishlist(name: String) {
        context.favoritesDataStore.edit { prefs ->
            val jsonString = prefs[WISHLISTS_KEY] ?: "{ \"ИЗБРАННОЕ\": [] }"
            val json = JSONObject(jsonString)
            if (!json.has(name)) {
                json.put(name, JSONArray())
            }
            prefs[WISHLISTS_KEY] = json.toString()
        }
    }

    override suspend fun deleteWishlist(name: String) {
        if (name == "ИЗБРАННОЕ") return
        context.favoritesDataStore.edit { prefs ->
            val jsonString = prefs[WISHLISTS_KEY] ?: return@edit
            val json = JSONObject(jsonString)
            json.remove(name)
            prefs[WISHLISTS_KEY] = json.toString()
        }
    }

    override suspend fun moveProduct(productId: String, fromWishlist: String, toWishlist: String) {
        context.favoritesDataStore.edit { prefs ->
            val jsonString = prefs[WISHLISTS_KEY] ?: return@edit
            val json = JSONObject(jsonString)
            
            // Remove from source
            if (json.has(fromWishlist)) {
                val fromArray = json.getJSONArray(fromWishlist)
                val newList = mutableListOf<String>()
                for (i in 0 until fromArray.length()) {
                    val id = fromArray.getString(i)
                    if (id != productId) newList.add(id)
                }
                json.put(fromWishlist, JSONArray(newList))
            }
            
            // Add to destination
            val toArray = if (json.has(toWishlist)) {
                json.getJSONArray(toWishlist)
            } else {
                JSONArray().also { json.put(toWishlist, it) }
            }
            
            val toList = mutableListOf<String>()
            for (i in 0 until toArray.length()) {
                toList.add(toArray.getString(i))
            }
            if (!toList.contains(productId)) {
                toList.add(productId)
            }
            json.put(toWishlist, JSONArray(toList))
            
            prefs[WISHLISTS_KEY] = json.toString()
        }
    }

    override suspend fun isFavorite(productId: String): Boolean {
        val allFavs = favoritesFlow.first()
        return allFavs.contains(productId)
    }
}