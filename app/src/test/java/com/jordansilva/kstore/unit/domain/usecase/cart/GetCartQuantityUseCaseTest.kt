package com.jordansilva.kstore.unit.domain.usecase.cart

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.repository.CartRepository
import com.jordansilva.kstore.domain.usecase.cart.GetCartQuantityUseCase
import com.jordansilva.kstore.helper.FakeCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.spy

class GetCartQuantityUseCaseTest {

    private lateinit var sut: GetCartQuantityUseCase
    private lateinit var fakeRepository: CartRepository

    @Before
    fun setUp() {
        fakeRepository = spy(FakeCartRepository())
        sut = GetCartQuantityUseCase(fakeRepository)
    }

    @Test
    fun `given an empty cart, when I start observing the quantity of items in the cart, then it should return zero`() {
        assertThat(sut.execute().value).isEqualTo(0)
    }

    @Test
    fun `given an empty cart and 10 new products are added, when I observe the quantity of items in the cart, then it should return the quantity of the items added in the cart`() =
        runBlockingTest {
            val numItemsToAdd = 10
            val quantityList = mutableListOf<Int>()

            val job = launch { sut.execute().take(numItemsToAdd + 1).collect { quantityList.add(it) } }
            repeat(numItemsToAdd) { fakeRepository.addProduct(it.toString()) }

            assertThat(quantityList).hasSize(numItemsToAdd + 1)
            assertThat(quantityList).isInOrder()
            job.cancelAndJoin()
        }


}