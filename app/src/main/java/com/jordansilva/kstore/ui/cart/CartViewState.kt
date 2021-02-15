package com.jordansilva.kstore.ui.cart

import com.jordansilva.kstore.ui.model.CartViewData

sealed class CartViewState {
    data class Updated(val cart: CartViewData) : CartViewState()
    object EmptyCart : CartViewState()
}

