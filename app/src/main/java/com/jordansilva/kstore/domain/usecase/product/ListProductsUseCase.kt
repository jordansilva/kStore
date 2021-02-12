package com.jordansilva.kstore.domain.usecase.product

import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository

class ListProductsUseCase(private val repository: ProductsRepository) {

    fun execute(): ListProductsResult {
        val products = repository.listAllProducts()
        return ListProductsResult.Products(products)
    }

    sealed class ListProductsResult {
        data class Products(val data: List<Product>): ListProductsResult()
    }
}

