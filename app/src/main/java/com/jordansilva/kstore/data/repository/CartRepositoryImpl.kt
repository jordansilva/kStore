package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.data.repository.datasource.CartDataSource
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.repository.ProductsRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productsRepository: ProductsRepository
) : CartRepository {

    override fun getCart() = cartDataSource.observeCart()

    override fun addProduct(id: String): Boolean {
        val product = productsRepository.getProduct(id) ?: return false
        return cartDataSource.addProduct(product)
    }

    override fun removeProduct(id: String): Boolean {
        cartDataSource.removeProductById(id)
        return true

    }
}