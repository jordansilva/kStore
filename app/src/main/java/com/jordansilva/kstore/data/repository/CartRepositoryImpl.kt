package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl(private val productsRepository: ProductsRepository) : CartRepository {

    private val cart = Cart()
    private val _cartFlow = MutableSharedFlow<Cart>(1)
    private val _cartQuantityFlow = MutableStateFlow(0)

    init {
        _cartFlow.tryEmit(cart)
    }

    override fun getCart() = _cartFlow.asSharedFlow()
    override fun cartQuantity() = _cartQuantityFlow.asStateFlow()

    override fun addProduct(id: String): Boolean {
        val product = productsRepository.getProduct(id) ?: return false
        cart.add(product)
        _cartFlow.tryEmit(cart)
        _cartQuantityFlow.tryEmit(cart.quantityItems())
        return true
    }

    override fun removeProduct(id: String): Boolean {
        cart.remove(id)
        _cartFlow.tryEmit(cart)
        _cartQuantityFlow.tryEmit(cart.quantityItems())
        return true

    }
}