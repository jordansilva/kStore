package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.repository.ProductsRepository

class CartRepositoryImpl(private val productsRepository: ProductsRepository) : CartRepository {

    private val cart = Cart()

    override fun getCart(): Cart {
        return cart
    }

    override fun addProduct(id: String): Cart {
        val product = productsRepository.getProduct(id)
        cart.products.add(product)
        return cart
    }

    override fun removeProduct(id: String): Cart {
        cart.products.removeAll { it.id == id } //O(N)
        return cart
    }
}