package com.jordansilva.kstore.domain.usecase.cart

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository

class AddProductToCartUseCase(private val repository: CartRepository) {

    fun execute(id: String): Cart {
        return repository.addProduct(id)
    }
}

