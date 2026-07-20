package com.belleyou.feature.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.product.ui.ProductDetailUiState
import com.belleyou.core.util.ColorUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: String,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadProduct()
        observeFavorite()
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            favoritesRepository.favoritesFlow.collect { favorites ->
                _uiState.update { it.copy(isFavorite = productId in favorites) }
            }
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {

            val allProductsResult = try {
                Result.success(productRepository.getProductsFlow().first())
            } catch (e: Exception) {
                Result.failure(e)
            }

            if (allProductsResult.isFailure) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Ошибка загрузки данных"
                    )
                }
                return@launch
            }

            val allProducts = allProductsResult.getOrThrow()
            val product = allProducts.find { it.id == productId }

            if (product != null) {
                // Find variants: products with the same name but different articles
                // Unique by article to avoid duplicates if same product exists in different JSONs
                val variants = allProducts
                    .filter { it.name == product.name && it.id != product.id }
                    .distinctBy { it.article }

                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        product = product,
                        selectedSize = product.sizes.firstOrNull().orEmpty(),
                        selectedColorName = extractColorFriendlyName(product.article),
                        colorVariants = variants,
                        relatedProducts = allProducts.shuffled().take(4),
                        matchingProducts = allProducts.shuffled().take(4),
                        recentlyViewed = allProducts.shuffled().take(4),
                        error = null
                    )
                }

            } else {

                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Товар не найден"
                    )
                }
            }
        }
    }

    private fun extractColorFriendlyName(article: String): String {
        val colorPart = ColorUtils.extractColorName(article)
        return when (colorPart.uppercase()) {
            "BELYY" -> "Белый"
            "PLOMBIR" -> "Пломбир"
            "CHERNYY" -> "Черный"
            "TEMNO_BEZHEVYY", "TEMNO" -> "Темно-бежевый"
            "BEZHEVYY" -> "Бежевый"
            "KORALLOVYY" -> "Коралловый"
            "LAVANDOVYY" -> "Лавандовый"
            "GOLUBOY" -> "Голубой"
            "SERYY" -> "Серый"
            "BORD" -> "Бордо"
            "MALINA" -> "Малина"
            "SHOKOLAD" -> "Шоколад"
            "HAKI" -> "Хаки"
            else -> colorPart.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    fun selectSize(size: String) {
        _uiState.update {
            it.copy(selectedSize = size)
        }
    }

    fun toggleDescription() {
        _uiState.update {
            it.copy(showDescription = !it.showDescription)
        }
    }

    fun closeDescription() {
        _uiState.update {
            it.copy(showDescription = false)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return
        val size = _uiState.value.selectedSize

        viewModelScope.launch {
            cartRepository.add(product.id, size)
        }
    }
}