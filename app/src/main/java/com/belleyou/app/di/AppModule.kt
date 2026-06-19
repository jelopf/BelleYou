package com.belleyou.app.di

import com.belleyou.app.features.cart.data.repository.CartRepositoryImpl
import com.belleyou.app.features.cart.domain.repository.CartRepository
import com.belleyou.app.features.cart.domain.usecase.AddToCartUseCase
import com.belleyou.app.features.cart.domain.usecase.CalculateCartTotalUseCase
import com.belleyou.app.features.cart.domain.usecase.ClearCartUseCase
import com.belleyou.app.features.cart.domain.usecase.DecreaseQuantityUseCase
import com.belleyou.app.features.cart.domain.usecase.GetCartItemsUseCase
import com.belleyou.app.features.cart.domain.usecase.IncreaseQuantityUseCase
import com.belleyou.app.features.cart.domain.usecase.RemoveFromCartUseCase
import com.belleyou.app.features.home.presentation.viewmodel.HomeViewModel
import com.belleyou.app.features.product.domain.data.repository.ProductRepositoryImpl
import com.belleyou.app.features.product.domain.repository.ProductRepository
import com.belleyou.app.features.product.domain.usecase.GetProductUseCase
import com.belleyou.app.features.product.domain.usecase.GetProductsUseCase
import com.belleyou.app.features.product.presentation.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Repository
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

    // ViewModel
    viewModel {
        HomeViewModel(get())
    }

    viewModel { (productId: Int) ->
        ProductDetailViewModel(productId, get())
    }
}