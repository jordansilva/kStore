package com.jordansilva.kstore.unit.domain.usecase.cart

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.usecase.cart.RemoveProductFromCartUseCase
import com.jordansilva.kstore.helper.FakeCartRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class RemoveProductFromCartUseCaseTest {

    private lateinit var sut: RemoveProductFromCartUseCase
    private lateinit var fakeRepository: CartRepository

    @Before
    fun setUp() {
        fakeRepository = Mockito.spy(FakeCartRepository())
        sut = RemoveProductFromCartUseCase(fakeRepository)

        repeat(INITIAL_QUANTITY_ITEMS) { fakeRepository.addProduct(it.toString()) }
    }

    private fun cartQuantity() = fakeRepository.getCart().replayCache.first().quantityItems()

    @Test
    fun `given a valid product id, when I remove this product from the cart, then it should remove a product in the repository and return true`() {
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS)

        val validId = "1"
        assertThat(sut.execute(validId)).isTrue()
        verify(fakeRepository).removeProduct(validId)
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS - 1)
    }

    @Test
    fun `given a valid product id, when I remove this product twice from the cart, then it should remove a single product in the repository and return true`() {
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS)

        val validId = "1"
        assertThat(sut.execute(validId)).isTrue()
        assertThat(sut.execute(validId)).isTrue()

        verify(fakeRepository, times(2)).removeProduct(validId)
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS - 1)
    }

    @Test
    fun `given the car an invalid product id, when I remove this product from the cart, then it should remove a product in the repository and return true`() {
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS)

        val invalidId = "A"
        assertThat(sut.execute(invalidId)).isTrue()
        verify(fakeRepository).removeProduct(invalidId)
        assertThat(cartQuantity()).isEqualTo(INITIAL_QUANTITY_ITEMS)
    }

    private companion object {
        const val INITIAL_QUANTITY_ITEMS = 10
    }
}