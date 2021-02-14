package com.jordansilva.kstore.data.repository

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import org.json.JSONArray
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal


class ProductsRepositoryImplTest {

    private lateinit var repository: ProductsRepositoryImpl
    private lateinit var remoteDataSource: ProductsRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = object : ProductsRemoteDataSource {
            override fun fetchProducts(): JSONArray = JSONArray()
        }
        repository = ProductsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun listAllProducts() {
        val products = repository.listAllProducts()
        assertThat(products).isEmpty()
    }

    @Test
    fun getProduct() {
        val expected = Product("1", "Product X", "Category", Product.Price(BigDecimal.ZERO, "brl"))
        val product = repository.getProduct("1")
        assertThat(product).isEqualTo(expected)
    }
}