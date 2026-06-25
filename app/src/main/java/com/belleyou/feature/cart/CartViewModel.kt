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

                val cartItems = cartMap.mapNotNull { (productId, count) ->

                    val product = products.find { it.id == productId }

                    product?.let {
                        CartItem(
                            product = it,
                            quantity = count
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

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.add(productId)
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.remove(productId)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            cartRepository.remove(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clear()
        }
    }
}