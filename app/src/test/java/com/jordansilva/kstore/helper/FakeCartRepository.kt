package com.jordansilva.kstore.helper

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

open class FakeCartRepository : CartRepository {

    private val cart = Cart()

    private val cartFlow = MutableSharedFlow<Cart>(1)
    private val products = mutableMapOf<String, Int>()
    private val quantity = MutableStateFlow(products.size)

    init {
        cartFlow.tryEmit(cart)
    }

    override fun getCart(): SharedFlow<Cart> = cartFlow.asSharedFlow()

    override fun addProduct(id: String): Boolean {
        val itemQuantity = products[id] ?: 0
        products[id] = itemQuantity + 1
        quantity.tryEmit(products.values.sum())

        cart.add(FakeInstances.makeProduct(id))
        cartFlow.tryEmit(cart)

        return true
    }

    override fun removeProduct(id: String): Boolean {
        products[id]?.let { quantity ->
            val itemQuantity = quantity - 1
            if (itemQuantity > 0) {
                products[id] = itemQuantity
            } else {
                products.remove(id)
            }
        }

        quantity.tryEmit(products.values.sum())
        cart.remove(id)
        cartFlow.tryEmit(cart)
        return true
    }
}