package com.jordansilva.kstore.data.repository

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class CartRepositoryImplTest {

    private lateinit var repository: CartRepositoryImpl
    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setUp() {
        productsRepository = object : ProductsRepository {
            override fun listAllProducts(): List<Product> = TODO("Not yet implemented")
            override fun getProduct(id: String): Product =
                Product(id, "Product $id", "Category $id", Product.Price(BigDecimal(0.0), "brl"))
        }
        repository = CartRepositoryImpl(productsRepository)
    }

    private val productsInCart get() = repository.getCart().products

    @Test
    fun `when add a product to the shopping cart, the amount of products in the cart must increase`() {
        assertThat(productsInCart).isEmpty()

        val isAdded = repository.addProduct("A")
        assertThat(isAdded).isTrue()

        val expectedProduct = productsRepository.getProduct("A")
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