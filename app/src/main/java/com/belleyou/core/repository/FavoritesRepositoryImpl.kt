package com.belleyou.core.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("favorites")

class FavoritesRepositoryImpl(
    private val context: Context
) : FavoritesRepository {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorites_ids")

    override val favoritesFlow: Flow<Set<Int>> =
        context.dataStore.data.map { prefs ->
            prefs[FAVORITES_KEY]
                ?.mapNotNull { it.toIntOrNull() }
                ?.toSet()
                ?: emptySet()
        }

    override suspend fun toggleFavorite(productId: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]
                ?.toMutableSet()
                ?: mutableSetOf()

            val id = productId.toString()

            if (current.contains(id)) {
                current.remove(id)
            } else {
                current.add(id)
            }

            prefs[FAVORITES_KEY] = current
        }
    }

    override suspend fun isFavorite(productId: Int): Boolean {
        val current = context.dataStore.data
            .map { prefs ->
                prefs[FAVORITES_KEY]
                    ?.contains(productId.toString())
                    ?: false
            }

        return current.first()
    }
}