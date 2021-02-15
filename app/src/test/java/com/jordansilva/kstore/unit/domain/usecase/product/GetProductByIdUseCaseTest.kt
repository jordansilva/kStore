package com.jordansilva.kstore.unit.domain.usecase.product

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.repository.ProductsRepository
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase
import com.jordansilva.kstore.domain.usecase.product.GetProductByIdUseCase.GetProductByIdResult
import com.jordansilva.kstore.helper.TestUtil
import org.junit.Before
import org.junit.Test

class GetProductByIdUseCaseTest {

    private lateinit var sut: GetProductByIdUseCase

    @Before
    fun setUp() {
        val fakeRepository = object : ProductsRepository {
            override fun listAllProducts(): List<Product> = TODO()
            override fun getProduct(id: String): Product? = if (id == VALID_PRODUCT.id) VALID_PRODUCT else null
        }

        sut = GetProductByIdUseCase(fakeRepository)
    }

    @Test
    fun `given the product id is valid, when I retrieve the product for that id, then it should return a valid product`() {
        val result = sut.execute(VALID_PRODUCT.id)
        assertThat(result).isInstanceOf(GetProductByIdResult.Found::class.java)

        result as GetProductByIdResult.Found
        assertThat(result.data).isEqualTo(VALID_PRODUCT)
    }

    @Test
    fun `given the product id is invalid, when I retrieve the product for that id, then it should return product not found`() {
        val result = sut.execute("INVALID")
        assertThat(result).isInstanceOf(GetProductByIdResult.NotFound::class.java)
    }

    private companion object {
        val VALID_PRODUCT = TestUtil.makeProduct("A")
    }
}