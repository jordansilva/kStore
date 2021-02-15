package com.jordansilva.kstore.unit.data.repository

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.data.local.CartDataSourceImpl
import com.jordansilva.kstore.data.repository.CartRepositoryImpl
import com.jordansilva.kstore.data.repository.datasource.CartDataSource
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.helper.FakeInstances
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class CartRepositoryImplTest {

    private lateinit var repository: CartRepositoryImpl
    private lateinit var cartDataSource: CartDataSource
    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setUp() {
        cartDataSource = CartDataSourceImpl()
        productsRepository = object : ProductsRepository {
            override fun listAllProducts(): List<Product> = TODO("Not yet implemented")
            override fun getProduct(id: String): Product =
                Product(id, "Product $id", "Category $id", Product.Price(BigDecimal(0.0), "brl"))
        }
        repository = CartRepositoryImpl(cartDataSource, productsRepository)
    }

    private val productsInCart get() = runBlocking { repository.getCart().first().products }

    @Test
    fun `when add a product to the shopping cart, the amount of products in the cart must increase`() {
        assertThat(productsInCart).isEmpty()

        val isAdded = repository.addProduct("A")
        assertThat(isAdded).isTrue()

        val product = productsRepository.getProduct("A")!!
        val expectedProduct = FakeInstances.makeCartProduct(product)
        assertThat(productsInCart[0]).isEqualTo(expectedProduct)
    }

    @Test
    fun `when remove a product from the shopping cart, the amount of products in the cart must reduce`() {
        repository.addProduct("A")
        assertThat(productsInCart).hasSize(1)

        repository.removeProduct("A")
        assertThat(productsInCart).isEmpty()
    }

    @Test
    fun `when remove an invalid product from the shopping cart, the amount of products in the cart should not change`() {
        repository.addProduct("A")
        assertThat(productsInCart).hasSize(1)

        repository.removeProduct("X")
        assertThat(productsInCart).hasSize(1)
    }
}