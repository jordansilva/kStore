package com.jordansilva.kstore.domain.usecase.cart

import com.jordansilva.kstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow

class GetCartQuantityUseCase(private val repository: CartRepository) {

    fun execute(): StateFlow<Int> {
        return repository.cartQuantity()
    }
}
