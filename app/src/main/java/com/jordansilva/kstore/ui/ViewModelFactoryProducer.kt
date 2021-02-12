package com.jordansilva.kstore.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordansilva.kstore.data.remote.ProductsRemoteDataSourceImpl
import com.jordansilva.kstore.data.remote.RawDataSource
import com.jordansilva.kstore.data.repository.ProductsRepositoryImpl
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.ui.home.HomeViewModel

class ViewModelFactoryProducer(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass.simpleName) {
            HomeViewModel::class.java.simpleName -> {
                val remoteDataSource = ProductsRemoteDataSourceImpl(RawDataSource(context.resources))
                val productsRepository = ProductsRepositoryImpl(remoteDataSource)
                val listProductsUseCase = ListProductsUseCase(productsRepository)
                modelClass.getConstructor(ListProductsUseCase::class.java).newInstance(listProductsUseCase)
            }
            else -> super.create(modelClass)
        }
    }
}