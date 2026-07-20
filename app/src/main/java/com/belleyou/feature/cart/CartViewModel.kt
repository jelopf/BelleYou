package com.belleyou.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.cart.domain.CartItem
import com.belleyou.feature.cart.ui.CartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {

            combine(
                cartRepository.cartFlow,
                productRepository.getProductsFlow(),
                favoritesRepository.favoritesFlow
            ) { cartMap, products, favorites ->

                val cartItems = cartMap.mapNotNull { (key, count) ->

                    val product = products.find { it.id == key.productId }

                    product?.let {
                        CartItem(
                            product = it,
                            quantity = count,
                            selectedSize = key.size
                        )
                    }
                }

                val currentSelectedKeys = cartItems.map { it.product.id to it.selectedSize }.toSet()
                val validSelectedItems = _uiState.value.selectedItems.intersect(currentSelectedKeys)

                val totalPrice = cartItems
                    .filter { (it.product.id to it.selectedSize) in validSelectedItems }
                    .sumOf { (it.product.price ?: 0) * it.quantity }

                _uiState.value.copy(
                    cartItems = cartItems,
                    selectedItems = validSelectedItems,
                    totalPrice = totalPrice,
                    recommendedProducts = products.shuffled().take(6),
                    favorites = favorites
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun toggleItemSelection(productId: String, size: String) {
        _uiState.update { state ->
            val key = productId to size
            val newSelectedItems = if (key in state.selectedItems) {
                state.selectedItems - key
            } else {
                state.selectedItems + key
            }
            state.copy(selectedItems = newSelectedItems).calculatePrice()
        }
    }

    fun toggleSelectAll(select: Boolean) {
        _uiState.update { state ->
            val newSelected = if (select) {
                state.cartItems.map { it.product.id to it.selectedSize }.toSet()
            } else {
                emptySet()
            }
            state.copy(selectedItems = newSelected).calculatePrice()
        }
    }

    private fun CartUiState.calculatePrice(): CartUiState {
        val price = cartItems
            .filter { (it.product.id to it.selectedSize) in selectedItems }
            .sumOf { (it.product.price ?: 0) * it.quantity }
        return copy(totalPrice = price)
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(productId)
        }
    }

    fun increaseQuantity(productId: String, size: String) {
        viewModelScope.launch {
            cartRepository.add(productId, size)
        }
    }

    fun decreaseQuantity(productId: String, size: String) {
        viewModelScope.launch {
            cartRepository.remove(productId, size)
        }
    }

    fun removeFromCart(productId: String, size: String) {
        viewModelScope.launch {
            cartRepository.removeAll(productId, size)
        }
    }
}
