package com.jordansilva.kstore.data.local

import com.jordansilva.kstore.data.repository.ProductsLocalDataSource
import com.jordansilva.kstore.domain.model.Product

class ProductsDataSourceImpl : ProductsLocalDataSource {

    private val productsMap = mutableMapOf<String, Product>()

    override fun listProducts(): List<Product> = productsMap.values.toList()
    override fun getProductById(id: String): Product? {
        return productsMap[id]
    }

    override fun saveProduct(product: Product): Boolean {
        productsMap[product.id] = product
        return true
    }

    override fun removeProduct(product: Product): Boolean {
        productsMap.remove(product.id)
        return true
    }

}