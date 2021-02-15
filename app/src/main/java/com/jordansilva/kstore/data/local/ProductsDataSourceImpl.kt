package com.jordansilva.kstore.data.local

import com.jordansilva.kstore.data.repository.ProductsLocalDataSource
import com.jordansilva.kstore.domain.model.Product

class ProductsDataSourceImpl : ProductsLocalDataSource {

    override fun listProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: String): Product? {
        TODO("Not yet implemented")
    }
}