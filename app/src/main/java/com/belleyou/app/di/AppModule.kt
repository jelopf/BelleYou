package com.belleyou.app.di

import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.features.home.presentation.viewmodel.HomeViewModel
import com.belleyou.app.features.product.domain.data.repository.ProductRepositoryImpl
import com.belleyou.app.features.product.domain.repository.ProductRepository
import com.belleyou.app.features.product.domain.usecase.GetProductsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Repository
    single<ProductRepository> {
        ProductRepositoryImpl()
    }

    // UseCase
    single {
        GetProductsUseCase(get())
    }

    // ViewModel
    viewModel {
        HomeViewModel(get())
    }
}