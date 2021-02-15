package com.jordansilva.kstore.domain.repository

import com.jordansilva.kstore.domain.model.Product

interface ProductsRepository {
    suspend fun listAllProducts(): List<Product>
    fun getProduct(id: String): Product?
}
