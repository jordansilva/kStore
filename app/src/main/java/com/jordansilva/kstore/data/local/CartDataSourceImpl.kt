package com.jordansilva.kstore.data.local

import com.jordansilva.kstore.data.repository.datasource.CartDataSource
import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CartDataSourceImpl : CartDataSource {

    private var cart: Cart = Cart()
    private val _cartFlow = MutableSharedFlow<Cart>(1)

    init {
        _cartFlow.tryEmit(cart)
    }

    override fun observeCart(): SharedFlow<Cart> = _cartFlow.asSharedFlow()

    override fun newCart(): Cart {
        cart = Cart()
        _cartFlow.tryEmit(cart)
        return cart
    }

    override fun addProduct(product: Product): Boolean {
        cart.add(product)
        _cartFlow.tryEmit(cart)
        return true
    }

    override fun removeProductById(id: String): Boolean {
        cart.remove(id)
        _cartFlow.tryEmit(cart)
        return true
    }
}