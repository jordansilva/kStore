package com.jordansilva.kstore.data.repository.datasource

import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.model.Product
import kotlinx.coroutines.flow.SharedFlow

interface CartDataSource {
    fun getCart(): Cart
    fun observeCart(): SharedFlow<Cart>
    fun addProduct(product: Product): Boolean
    fun removeProductById(id: String): Boolean
}