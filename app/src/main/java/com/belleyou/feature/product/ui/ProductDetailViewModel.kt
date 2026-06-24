package com.belleyou.feature.product.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.toUiModel
import com.belleyou.feature.product.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: Int,
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ProductDetailUiState(isLoading = true))

    val uiState = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {

            val product = getProductUseCase(productId)?.toUiModel()

            if (product != null) {

                _uiState.value = ProductDetailUiState(
                    isLoading = false,
                    product = product,
                    selectedSize = product.sizes.firstOrNull().orEmpty(),
                    isFavorite = false,
                    showDescription = false
                )

            } else {

                _uiState.value = ProductDetailUiState(
                    isLoading = false,
                    error = "Product not found"
                )
            }
        }
    }

    fun selectSize(size: String) {
        _uiState.value = _uiState.value.copy(
            selectedSize = size
        )
    }

    fun toggleFavorite() {
        _uiState.value = _uiState.value.copy(
            isFavorite = !_uiState.value.isFavorite
        )
    }

    fun toggleDescription() {
        _uiState.value = _uiState.value.copy(
            showDescription = !_uiState.value.showDescription
        )
    }

    fun closeDescription() {
        _uiState.value = _uiState.value.copy(
            showDescription = false
        )
    }
}