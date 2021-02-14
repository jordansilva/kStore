package com.jordansilva.kstore.unit.domain.usecase.product

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase.ListProductsResult
import com.jordansilva.kstore.helper.FakeInstances
import org.junit.Before
import org.junit.Test

class ListProductsUseCaseTest {

    private lateinit var sut: ListProductsUseCase
    private val fakeRepository = object : ProductsRepository {
        val list = mutableListOf<Product>()
        fun populate() {
            list.clear()
            repeat(10) { list.add(FakeInstances.makeProduct(it.toString())) }
        }

        override fun listAllProducts(): List<Product> = list
        override fun getProduct(id: String): Product = TODO("Not yet implemented")
    }

    @Before
    fun setUp() {
        sut = ListProductsUseCase(fakeRepository)
    }

    @Test
    fun `given the database has no product, when I retrieve the list of products, then it should return an empty list of products`() {
        val result = sut.execute()
        assertThat(result).isInstanceOf(ListProductsResult.Products::class.java)
        result as ListProductsResult.Products

        assertThat(result.data).isEmpty()
    }

    @Test
    fun `given the database was populated with products, when I retrieve the list of products, then it should return a non-empty list with the respective products inside`() {
        val result = sut.execute()
        fakeRepository.populate()
        assertThat(result).isInstanceOf(ListProductsResult.Products::class.java)
        result as ListProductsResult.Products
        assertThat(result.data).isNotEmpty()
    }
}