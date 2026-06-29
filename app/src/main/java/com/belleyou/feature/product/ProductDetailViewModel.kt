package com.belleyou.feature.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.product.ui.ProductDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: Int,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {

            val product = productRepository
                .getProductsFlow()
                .first()
                .find { it.id == productId }

            if (product != null) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    product = product,
                    selectedSize = product.sizes.firstOrNull().orEmpty(),
                    error = null
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Product not found"
                )
            }
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

    fun addToCart() {
        val product = _uiState.value.product ?: return
        val size = _uiState.value.selectedSize

        viewModelScope.launch {
            cartRepository.add(product.id, size)
        }
    }
}