package com.jordansilva.kstore.unit.data.repository

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.data.repository.ProductsLocalDataSource
import com.jordansilva.kstore.data.repository.ProductsRepositoryImpl
import com.jordansilva.kstore.data.repository.datasource.ProductsRemoteDataSource
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.helper.FakeInstances
import org.json.JSONArray
import org.junit.Before
import org.junit.Test


class ProductsRepositoryImplTest {

    private lateinit var repository: ProductsRepositoryImpl
    private lateinit var localDataSource: ProductsLocalDataSource
    private lateinit var remoteDataSource: ProductsRemoteDataSource

    @Before
    fun setUp() {
        localDataSource = object : ProductsLocalDataSource {
            override fun listProducts(): List<Product> = listOf(PRODUCT_A)
            override fun getProductById(id: String): Product = FakeInstances.makeProduct(id)
        }
        remoteDataSource = object : ProductsRemoteDataSource {
            override fun fetchProducts(): JSONArray = JSONArray(arrayOf(PRODUCT_A))
        }
        repository = ProductsRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun listAllProducts() {
        val products = repository.listAllProducts()
        assertThat(products).hasSize(1)
    }

    @Test
    fun getProduct() {
        val expected = PRODUCT_A
        val product = repository.getProduct(PRODUCT_A.id)
        assertThat(product).isEqualTo(expected)
    }

    private companion object {
        private val PRODUCT_A = FakeInstances.makeProduct("A")
    }
}