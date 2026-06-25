package com.belleyou.core.di

import com.belleyou.core.repository.CartRepository
import com.belleyou.core.repository.CartRepositoryImpl
import com.belleyou.core.repository.FavoritesRepository
import com.belleyou.core.repository.FavoritesRepositoryImpl
import com.belleyou.core.repository.ProductRepository
import com.belleyou.core.repository.ProductRepositoryImpl
import com.belleyou.core.assets.ProductJsonDataSource
import com.belleyou.feature.cart.CartViewModel
import com.belleyou.feature.category.CategoryViewModel
import com.belleyou.feature.home.HomeViewModel
import com.belleyou.feature.product.ProductDetailViewModel
import com.belleyou.feature.recommendations.RecommendationsViewModel
import com.belleyou.feature.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Data Sources
    single {
        ProductJsonDataSource(get())
    }

    // Repositories
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CartRepository> {
        CartRepositoryImpl(get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }

    // ViewModels
    viewModel {
        HomeViewModel(
            get(),
            get()
        )
    }

    viewModel {
        CategoryViewModel(
            get(),
            get()
        )
    }

    viewModel {
        RecommendationsViewModel(
            get(),
            get(),
            get()
        )
    }

    viewModel {
        CartViewModel(
            get(),
            get()
        )
    }

    viewModel { (productId: Int) ->
        ProductDetailViewModel(
            productId,
            get(),
            get()
        )
    }

    viewModel {
        WishlistViewModel(
            get(),
            get(),
            get()
        )
    }
}