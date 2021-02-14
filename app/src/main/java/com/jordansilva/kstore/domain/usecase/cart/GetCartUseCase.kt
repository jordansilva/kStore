package com.jordansilva.kstore.domain.usecase.cart

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharedFlow

class GetCartUseCase(private val repository: CartRepository) {

    fun execute(): SharedFlow<Cart> {
        return repository.getCart()
    }
}
