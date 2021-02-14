package com.jordansilva.kstore.domain.usecase.product

import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository

class GetProductByIdUseCase(private val repository: ProductsRepository) {

    fun execute(id: String): GetProductByIdResult {
        return try {
            val product = repository.getProduct(id)
            return product?.let { GetProductByIdResult.Found(product) } ?: GetProductByIdResult.NotFound
        } catch (e: Throwable) {
            GetProductByIdResult.NotFound
        }
    }

    sealed class GetProductByIdResult {
        data class Found(val data: Product): GetProductByIdResult()
        object NotFound: GetProductByIdResult()
    }
}

