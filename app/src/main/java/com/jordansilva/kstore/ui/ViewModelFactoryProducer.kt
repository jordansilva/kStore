package com.jordansilva.kstore.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordansilva.kstore.data.remote.ProductsRemoteDataSourceImpl
import com.jordansilva.kstore.data.remote.RawDataSource
import com.jordansilva.kstore.data.repository.ProductsRemoteDataSource
import com.jordansilva.kstore.data.repository.ProductsRepositoryImpl
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.ui.home.HomeViewModel
import com.jordansilva.kstore.ui.product.ProductDetailViewModel

class ViewModelFactoryProducer(context: Context) : ViewModelProvider.NewInstanceFactory() {

    init {
        UglyStaticClasses.initClasses(context)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass.simpleName) {
            HomeViewModel::class.java.simpleName -> {
                val listProductsUseCase = ListProductsUseCase(UglyStaticClasses.productsRepository!!)
                modelClass.getConstructor(ListProductsUseCase::class.java).newInstance(listProductsUseCase)
            }
            ProductDetailViewModel::class.java.simpleName -> {
                val getProductByIdUseCase = GetProductByIdUseCase(UglyStaticClasses.productsRepository!!)
                modelClass.getConstructor(GetProductByIdUseCase::class.java).newInstance(getProductByIdUseCase)
            }
            else -> super.create(modelClass)
        }
    }
}

internal object UglyStaticClasses {

    var remoteDataSource: ProductsRemoteDataSource? = null
    var productsRepository: ProductsRepository? = null

    fun initClasses(context: Context) {
        if (remoteDataSource == null) remoteDataSource = ProductsRemoteDataSourceImpl(RawDataSource(context.resources))
        if (remoteDataSource != null && productsRepository == null) productsRepository = ProductsRepositoryImpl(remoteDataSource!!)
    }
}