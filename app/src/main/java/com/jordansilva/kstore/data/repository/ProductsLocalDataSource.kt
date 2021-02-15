package com.jordansilva.kstore.data.repository

import com.jordansilva.kstore.domain.model.Product

interface ProductsLocalDataSource {
    fun listProducts(): List<Product>
    fun getProductById(id: String): Product?
}
