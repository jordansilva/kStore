package com.jordansilva.kstore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase.ListProductsResult
import com.jordansilva.kstore.ui.model.ProductViewData

class HomeViewModel(private val listProductsUseCase: ListProductsUseCase) : ViewModel() {

    private val _products = MutableLiveData<List<ProductViewData>>()
    val products: LiveData<List<ProductViewData>> = _products

    init {
        listProducts()
    }

    private fun listProducts() {
        when (val result = listProductsUseCase.execute()) {
            is ListProductsResult.Products -> _products.postValue(result.data.map(ProductViewData::fromProduct))
        }
    }


}