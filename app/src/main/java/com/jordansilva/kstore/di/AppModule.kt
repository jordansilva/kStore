package com.jordansilva.kstore.di

import com.jordansilva.kstore.data.remote.ProductsRemoteDataSourceImpl
import com.jordansilva.kstore.data.remote.RawDataSource
import com.jordansilva.kstore.data.repository.ProductsRemoteDataSource
import com.jordansilva.kstore.data.repository.ProductsRepositoryImpl
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.GetCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.RemoveProductFromCartUseCase
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.ui.home.HomeViewModel
import com.jordansilva.kstore.ui.product.ProductDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(listProductsUseCase = get()) }
    viewModel { ProductDetailViewModel(getProductByIdUseCase = get()) }
}

val domainModule = module {
    //Products
    factory { GetProductByIdUseCase(repository = get()) }
    factory { ListProductsUseCase(repository = get()) }

    //Cart
    factory { GetCartUseCase(repository = get()) }
    factory { AddProductToCartUseCase(repository = get()) }
    factory { RemoveProductFromCartUseCase(repository = get()) }
}

val dataModule = module {
    single { RawDataSource(resources = androidContext().resources) }
    single<ProductsRemoteDataSource> { ProductsRemoteDataSourceImpl(rawDataSource = get()) }
    single<ProductsRepository> { ProductsRepositoryImpl(remoteDataSource = get()) }
}