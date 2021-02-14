package com.jordansilva.kstore.unit.domain.usecase.cart

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.usecase.cart.AddProductToCartUseCase
import com.jordansilva.kstore.helper.FakeCartRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class AddProductToCartUseCaseTest {

    private lateinit var sut: AddProductToCartUseCase
    private lateinit var fakeRepository: CartRepository

    @Before
    fun setUp() {
        fakeRepository = Mockito.spy(FakeCartRepository())
        sut = AddProductToCartUseCase(fakeRepository)
    }

    @Test
    fun `given a valid product id, when I add this product to the cart, then it should add a product in the repository and return true`() {
        val validId = "1"
        assertThat(sut.execute(validId)).isTrue()
        verify(fakeRepository).addProduct(validId)
        assertThat(fakeRepository.cartQuantity().value).isEqualTo(1)
    }

    @Test
    fun `given a valid product id, when I add this product two times to the cart, then it should add a single product with two items in the repository and return true`() {
        val validId = "1"
        assertThat(sut.execute(validId)).isTrue()
        assertThat(sut.execute(validId)).isTrue()

        verify(fakeRepository, times(2)).addProduct(validId)
        assertThat(fakeRepository.cartQuantity().value).isEqualTo(2)
    }

    @Test
    fun `given a cart with products, when I add a new product to the cart it, then it should add this to the cart as a new product and return false`() {
        sut.execute("1") //Initial Cart
        sut.execute("1") //Initial Cart
        assertThat(fakeRepository.cartQuantity().value).isEqualTo(2)

        val validId = "2"
        assertThat(sut.execute(validId)).isTrue()
        verify(fakeRepository).addProduct(validId)
        assertThat(fakeRepository.cartQuantity().value).isEqualTo(3)
    }
}