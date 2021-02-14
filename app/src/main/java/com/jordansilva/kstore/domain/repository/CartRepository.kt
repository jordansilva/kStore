package com.jordansilva.kstore.domain.repository

import com.jordansilva.kstore.domain.model.Cart
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    fun getCart(): Cart
    fun observeCart(): SharedFlow<Cart>
    fun addProduct(id: String): Boolean
    fun removeProduct(id: String): Boolean
}