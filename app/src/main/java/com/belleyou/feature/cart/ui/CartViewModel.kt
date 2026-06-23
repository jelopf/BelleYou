package com.belleyou.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belleyou.core.model.Product
import com.belleyou.feature.cart.domain.usecase.AddToCartUseCase
import com.belleyou.feature.cart.domain.usecase.CalculateCartTotalUseCase
import com.belleyou.feature.cart.domain.usecase.ClearCartUseCase
import com.belleyou.feature.cart.domain.usecase.DecreaseQuantityUseCase
import com.belleyou.feature.cart.domain.usecase.GetCartItemsUseCase
import com.belleyou.feature.cart.domain.usecase.IncreaseQuantityUseCase
import com.belleyou.feature.cart.domain.usecase.RemoveFromCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartItemsUseCase:
    GetCartItemsUseCase,

    private val addToCartUseCase:
    AddToCartUseCase,

    private val removeFromCartUseCase:
    RemoveFromCartUseCase,

    private val increaseQuantityUseCase:
    IncreaseQuantityUseCase,

    private val decreaseQuantityUseCase:
    DecreaseQuantityUseCase,

    private val clearCartUseCase:
    ClearCartUseCase,

    private val calculateCartTotalUseCase:
    CalculateCartTotalUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(CartUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {

        viewModelScope.launch {

            getCartItemsUseCase()
                .collect { cartItems ->

                    _uiState.value =
                        CartUiState(
                            cartItems = cartItems,
                            totalPrice =
                                calculateCartTotalUseCase(
                                    cartItems
                                )
                        )
                }
        }
    }

    fun addToCart(product:
                  Product
    ) {

        addToCartUseCase(product)
    }

    fun increaseQuantity(
        productId: Int
    ) {

        increaseQuantityUseCase(
            productId
        )
    }

    fun decreaseQuantity(
        productId: Int
    ) {

        decreaseQuantityUseCase(
            productId
        )
    }

    fun removeFromCart(
        productId: Int
    ) {

        removeFromCartUseCase(
            productId
        )
    }

    fun clearCart() {

        clearCartUseCase()
    }
}