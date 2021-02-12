package com.jordansilva.kstore.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase.GetProductByIdResult
import com.jordansilva.kstore.ui.BaseViewModel
import com.jordansilva.kstore.ui.model.ProductViewData

class ProductDetailViewModel(private val getProductByIdUseCase: GetProductByIdUseCase) : BaseViewModel() {

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _product = MutableLiveData<ProductDetailViewState>()
    val product: LiveData<ProductDetailViewState> = _product

    private fun getProductById(id: String) {
        launch {
            when (val result = getProductByIdUseCase.execute(id)) {
                is GetProductByIdResult.Found -> handleProductFound(result.data)
                is GetProductByIdResult.NotFound -> handleProductNotFound()
            }
        }.invokeOnCompletion { _loading.postValue(false) }
    }

    private fun handleProductFound(data: Product) {
        val productViewData = ProductViewData.fromProduct(data)
        val viewState = ProductDetailViewState.ProductDetail(productViewData)
        _product.postValue(viewState)
    }

    private fun handleProductNotFound() {
        _product.postValue(ProductDetailViewState.NoProductFound)
    }
}

