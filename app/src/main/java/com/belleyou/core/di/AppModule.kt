package com.belleyou.core.di

import com.belleyou.feature.cart.domain.CartRepository
import com.belleyou.feature.cart.data.CartRepositoryImpl
import com.belleyou.feature.cart.domain.usecase.AddToCartUseCase
import com.belleyou.feature.cart.domain.usecase.CalculateCartTotalUseCase
import com.belleyou.feature.cart.domain.usecase.ClearCartUseCase
import com.belleyou.feature.cart.domain.usecase.DecreaseQuantityUseCase
import com.belleyou.feature.cart.domain.usecase.GetCartItemsUseCase
import com.belleyou.feature.cart.domain.usecase.IncreaseQuantityUseCase
import com.belleyou.feature.cart.domain.usecase.RemoveFromCartUseCase
import com.belleyou.feature.cart.ui.CartViewModel
import com.belleyou.feature.category.ui.CategoryViewModel
import com.belleyou.feature.home.ui.HomeViewModel
import com.belleyou.feature.product.ui.ProductDetailViewModel
import com.belleyou.feature.product.domain.ProductRepository
import com.belleyou.feature.product.data.ProductRepositoryImpl
import com.belleyou.feature.product.domain.usecase.GetProductUseCase
import com.belleyou.feature.product.domain.usecase.GetProductsUseCase
import com.belleyou.feature.recommendations.ui.RecommendationsViewModel
import com.belleyou.feature.wishlist.ui.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ProductRepository> {
        ProductRepositoryImpl()
    }

    single<CartRepository> {
        CartRepositoryImpl()
    }

    // Product use cases
    single {
        GetProductsUseCase(get())
    }

    single {
        GetProductUseCase(get())
    }

    // Cart use cases
    single {
        GetCartItemsUseCase(get())
    }

    single {
        AddToCartUseCase(get())
    }

    single {
        RemoveFromCartUseCase(get())
    }

    single {
        IncreaseQuantityUseCase(get())
    }

    single {
        DecreaseQuantityUseCase(get())
    }

    single {
        ClearCartUseCase(get())
    }

    single {
        CalculateCartTotalUseCase()
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel { (productId: Int) ->
        ProductDetailViewModel(productId, get())
    }

    viewModel {
        RecommendationsViewModel(get())
    }

    viewModel {
        CartViewModel(
            getCartItemsUseCase = get(),
            addToCartUseCase = get(),
            removeFromCartUseCase = get(),
            increaseQuantityUseCase = get(),
            decreaseQuantityUseCase = get(),
            clearCartUseCase = get(),
            calculateCartTotalUseCase = get()
        )
    }

    viewModel {
        CategoryViewModel(get())
    }

    viewModel {
        WishlistViewModel()
    }
}