package com.jordansilva.kstore.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase.GetProductByIdResult
import com.jordansilva.kstore.ui.BaseViewModel

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : BaseViewModel() {

    private val _product = MutableLiveData<ProductDetailViewState>()
    val product: LiveData<ProductDetailViewState> = _product

    fun getProductById(id: String) {
        launch {
            when (val result = getProductByIdUseCase.execute(id)) {
                is GetProductByIdResult.Found -> handleProductFound(result.data)
                is GetProductByIdResult.NotFound -> handleProductNotFound()
            }
        }
    }

    fun addProductToBasket(id: String) {
        launch {
            val state = if (addProductToCartUseCase.execute(id)) ProductDetailViewState.AddedToCart else ProductDetailViewState.NotAddedToCart
            _product.postValue(state)
        }
    }

    private fun handleProductFound(data: Product) {
        val productViewData = ProductViewData.fromProduct(data)
        val viewState = ProductDetailViewState.ProductDetail(productViewData)
        _product.postValue(viewState)
    }

    private fun handleProductNotFound() {
        _product.postValue(ProductDetailViewState.NotFound)
    }
}

