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

    @Test
    fun getCart() {
        var cart = repository.getCart()
        assertThat(cart.products).isEmpty()

        cart = repository.addProduct("A")
        assertThat(cart.products).isNotEmpty()

        repository.addProduct("B")
        repository.addProduct("C")
        repository.addProduct("D")
        cart = repository.addProduct("D")
        assertThat(cart.products).hasSize(5)

        cart = repository.removeProduct("D")
        assertThat(cart.products).hasSize(3)

        cart = repository.removeProduct("D")
        assertThat(cart.products).hasSize(3)

        cart = repository.removeProduct("A")
        assertThat(cart.products).hasSize(2)
    }

    @Test
    fun addProduct() {
        val cart = repository.addProduct("A")
        assertThat(cart.products).hasSize(1)

        val expectedProduct = productsRepository.getProduct("A")
        assertThat(cart.products[0]).isEqualTo(expectedProduct)
    }

    @Test
    fun removeProduct() {
        var cart = repository.addProduct("B")
        assertThat(cart.products).hasSize(1)

        cart = repository.removeProduct("A")
        assertThat(cart.products).hasSize(1)

        cart = repository.removeProduct("B")
        assertThat(cart.products).isEmpty()
    }
}