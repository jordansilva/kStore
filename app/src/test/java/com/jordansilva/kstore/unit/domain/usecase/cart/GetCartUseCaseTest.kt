package com.jordansilva.kstore.unit.domain.usecase.cart

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.usecase.cart.GetCartUseCase
import com.jordansilva.kstore.helper.FakeCartRepository
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal

class GetCartUseCaseTest {

    private lateinit var sut: GetCartUseCase
    private lateinit var fakeRepository: CartRepository

    @Before
    fun setUp() {
        fakeRepository = Mockito.spy(FakeCartRepository())
        sut = GetCartUseCase(fakeRepository)
    }

    @Test
    fun `given an empty cart, when I start observing the cart, then it should return no products in it`() = runBlockingTest {
        val cart = sut.execute().replayCache.first()
        assertThat(cart.products).isEmpty()
        assertThat(cart.totalPrice()).isEqualTo(BigDecimal.ZERO)
        assertThat(cart.quantityItems()).isEqualTo(0)
    }

    @Test
    fun `given an empty cart and a product is added, when I start observing the cart, then it should receive empty cart and the cart with a product in it`() = runBlockingTest {
        val states = mutableListOf<Int>()
        val job = launch { sut.execute().take(2)
            .collect { states += it.quantityItems() } }

        fakeRepository.addProduct("A")

        assertThat(states).hasSize(2)
        assertThat(states.first()).isEqualTo(0)
        assertThat(states.last()).isEqualTo(1)
        job.cancelAndJoin()
    }

    @Test
    fun `given a cart with a product and this product is removed, when I start observing the cart, then it should receive an initial cart with this product and after an empty cart`() = runBlockingTest {
        fakeRepository.addProduct("A")

        val states = mutableListOf<Int>()
        val job = launch { sut.execute().take(5).collect { states += it.quantityItems() } }

        fakeRepository.removeProduct("A")
        assertThat(states.first()).isEqualTo(1)
        assertThat(states.last()).isEqualTo(0)
        job.cancelAndJoin()
    }


}