package com.jordansilva.kstore.domain.repository

import com.jordansilva.kstore.domain.model.Cart

interface CartRepository {
    fun getCart(): Cart
    fun addProduct(id: String): Cart
    fun removeProduct(id: String): Cart
}