package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.repository.ProductsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CartRepositoryImpl(private val productsRepository: ProductsRepository) : CartRepository {

    private val _cart = MutableSharedFlow<Cart>(1)
    val cart: SharedFlow<Cart> = _cart.asSharedFlow()

    init {
        _cart.tryEmit(Cart())
    }

    override fun getCart(): Cart {
        return cart.replayCache.last()
    }

    override fun observeCart() = cart

    override fun addProduct(id: String): Boolean {
        val newCart = getCart()
        val product = productsRepository.getProduct(id) ?: return false
        newCart.add(product)
        android.util.Log.d("Cart", "isEqual ${newCart == cart.replayCache.last()}")
        GlobalScope.launch { _cart.emit(newCart) }
        return true
    }

    override fun removeProduct(id: String): Boolean {
        val newCart = getCart()
        newCart.remove(id)
        GlobalScope.launch { _cart.emit(newCart) }
        return true

    }
}