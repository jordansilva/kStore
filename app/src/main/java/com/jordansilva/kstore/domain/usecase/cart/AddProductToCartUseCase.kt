package com.jordansilva.kstore.domain.usecase.cart

import com.jordansilva.kstore.domain.repository.CartRepository

class AddProductToCartUseCase(private val repository: CartRepository) {

    fun execute(id: String): Boolean {
        return repository.addProduct(id)
    }
}

