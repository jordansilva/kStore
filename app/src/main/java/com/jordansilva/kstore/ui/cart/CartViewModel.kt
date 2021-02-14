package com.jordansilva.kstore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.GetCartUseCase
import com.jordansilva.kstore.domain.usecase.cart.RemoveProductFromCartUseCase
import com.jordansilva.kstore.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : BaseViewModel() {

    val viewState: LiveData<CartViewState> = getCartUseCase.execute()
        .map { CartViewState.ItemsChanged(it.products.size) }
        .onEach { android.util.Log.d("CartViewModel", "asLiveData: ${it.quantity}") }
        .conflate()
        .asLiveData(viewModelScope.coroutineContext)


}

