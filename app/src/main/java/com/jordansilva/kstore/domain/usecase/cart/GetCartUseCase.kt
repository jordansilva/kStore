package com.jordansilva.kstore.domain.usecase.cart

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository

class GetCartUseCase(private val repository: CartRepository) {

    fun execute(): Cart {
        return repository.getCart()
    }
}

