package com.jordansilva.kstore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.GetCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.RemoveProductFromCartUseCase
import com.jordansilva.kstore.ui.BaseViewModel
import com.jordansilva.kstore.ui.model.CartViewData
import kotlinx.coroutines.flow.transform

class CartViewModel(
    getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : BaseViewModel() {

    val viewState: LiveData<CartViewState> = getCartUseCase.execute()
        .transform {
            if (it.products.isEmpty()) {
                emit(CartViewState.EmptyCart)
            } else {
                emit(CartViewState.Updated(CartViewData.fromCart(it)))
            }
        }
        .asLiveData()

    fun addProduct(id: String) {
        launch {
            addProductToCartUseCase.execute(id)
        }
    }

    fun removeProduct(id: String) {
        launch {
            removeProductFromCartUseCase.execute(id)
        }
    }

}

