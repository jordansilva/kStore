package com.jordansilva.kstore.domain.repository

import com.jordansilva.kstore.domain.model.Product

interface ProductsRepository {
    fun listAllProducts(): List<Product>
    fun getProduct(id: String): Product?
}
