package com.belleyou.core.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore by preferencesDataStore("favorites")

class FavoritesRepositoryImpl(
    private val context: Context
) : FavoritesRepository {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorites_ids")

    override val favoritesFlow: Flow<Set<String>> =
        context.favoritesDataStore.data.map { prefs ->
            prefs[FAVORITES_KEY] ?: emptySet()
        }

    override suspend fun toggleFavorite(productId: String) {
        context.favoritesDataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()

            if (current.contains(productId)) {
                current.remove(productId)
            } else {
                current.add(productId)
            }

            prefs[FAVORITES_KEY] = current
        }
    }

    override suspend fun isFavorite(productId: String): Boolean {
        val current = context.favoritesDataStore.data
            .map { prefs ->
                prefs[FAVORITES_KEY]?.contains(productId) ?: false
            }

        return current.first()
    }
}