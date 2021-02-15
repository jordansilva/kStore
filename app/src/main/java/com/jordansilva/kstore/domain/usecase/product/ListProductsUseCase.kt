package com.jordansilva.kstore.domain.usecase.product

import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository

class ListProductsUseCase(private val repository: ProductsRepository) {

    fun execute(): ListProductsResult {
        return try {
            val products = repository.listAllProducts()
            ListProductsResult.Products(products)
        } catch (ex: Exception) {
            ListProductsResult.Empty
        }
    }

    sealed class ListProductsResult {
        data class Products(val data: List<Product>) : ListProductsResult()
        object Empty : ListProductsResult()
    }
}

