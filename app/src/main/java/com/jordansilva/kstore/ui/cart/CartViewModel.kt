package com.jordansilva.kstore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.GetCartQuantityUseCase
import com.jordansilva.kstore.domain.usecase.cart.GetCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.RemoveProductFromCartUseCase
import com.jordansilva.kstore.ui.BaseViewModel
import com.jordansilva.kstore.ui.model.CartViewData
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class CartViewModel(
    getCartUseCase: GetCartUseCase,
    getCartQuantityUseCase: GetCartQuantityUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : BaseViewModel() {

    val quantityItems: LiveData<Int> = getCartQuantityUseCase.execute()
        .onEach { android.util.Log.d("CartViewModel", "quantity updated: $it") }
        .conflate()
        .asLiveData(viewModelScope.coroutineContext)


    val viewState: LiveData<CartViewState> = getCartUseCase.execute()
        .map { CartViewState.Updated(CartViewData.fromCart(it)) }
        .conflate()
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

