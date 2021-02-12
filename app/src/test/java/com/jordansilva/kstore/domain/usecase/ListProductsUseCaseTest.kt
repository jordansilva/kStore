package com.jordansilva.kstore.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase.ListProductsResult
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListProductsUseCaseTest {

    private lateinit var productsRepository: ProductsRepository
    private lateinit var listProductsUseCase: ListProductsUseCase

    @Before
    fun setUp() {
        productsRepository = object : ProductsRepository {
            override fun listAllProducts(): List<Product> = emptyList()
            override fun getProduct(id: String): Product {
                TODO("Not yet implemented")
            }
        }

        listProductsUseCase = ListProductsUseCase(productsRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when list products is executed without database, it should return an empty list`() {
        val result = listProductsUseCase.execute()

        assertThat(result).isInstanceOf(ListProductsResult.Products::class.java)
        result as ListProductsResult.Products

        assertThat(result.data).isEmpty()
    }
}