package com.belleyou.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.ProductRepository
import com.belleyou.feature.cart.domain.CartItem
import com.belleyou.feature.cart.ui.CartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
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
                productRepository.getProductsFlow()
            ) { cartMap, products ->

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

                val totalPrice = cartItems.sumOf {
                    it.product.price * it.quantity
                }

                CartUiState(
                    cartItems = cartItems,
                    totalPrice = totalPrice
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun increaseQuantity(productId: Int, size: String) {
        viewModelScope.launch {
            cartRepository.add(productId, size)
        }
    }

    fun decreaseQuantity(productId: Int, size: String) {
        viewModelScope.launch {
            cartRepository.remove(productId, size)
        }
    }

    fun removeFromCart(productId: Int, size: String) {
        viewModelScope.launch {
            cartRepository.removeAll(productId, size)
        }
    }
}