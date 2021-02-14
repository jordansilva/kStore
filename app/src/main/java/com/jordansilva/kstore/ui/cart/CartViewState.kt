package com.jordansilva.kstore.ui.cart

sealed class CartViewState {
    data class ItemsChanged(val quantity: Int) : CartViewState()
}