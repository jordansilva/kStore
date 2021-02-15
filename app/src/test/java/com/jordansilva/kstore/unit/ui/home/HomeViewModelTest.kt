package com.jordansilva.kstore.unit.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Product
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase
import com.jordansilva.kstore.domain.usecase.product.ListProductsUseCase.ListProductsResult
import com.jordansilva.kstore.helper.FakeInstances
import com.jordansilva.kstore.ui.home.HomeViewModel
import com.jordansilva.kstore.ui.model.ProductViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class HomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: HomeViewModel
    private lateinit var listProductsUseCaseMock: ListProductsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        listProductsUseCaseMock = mock(ListProductsUseCase::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given the list of products is not empty, when I start fetch the list of products in VM, then I must propagate this list to a view data`() = runBlockingTest {
        `when`(listProductsUseCaseMock.execute()).thenReturn(ListProductsResult.Products(LIST_PRODUCTS))
        sut = HomeViewModel(listProductsUseCaseMock)
        assertThat(sut.products.value).isEqualTo(LIST_PRODUCT_VIEWDATA)
    }

    @Test
    fun `given the list of products is empty, when I start fetch the list of products in VM, then I must propagate an empty list to a view data`() = runBlockingTest {
        `when`(listProductsUseCaseMock.execute()).thenReturn(ListProductsResult.Products(emptyList()))
        sut = HomeViewModel(listProductsUseCaseMock)
        assertThat(sut.products.value).isEmpty()
    }

    private companion object {
        private val LIST_PRODUCTS: List<Product> = listOf(
            FakeInstances.makeProduct("A"),
            FakeInstances.makeProduct("B"),
            FakeInstances.makeProduct("C")
        )

        private val LIST_PRODUCT_VIEWDATA: List<ProductViewData> = LIST_PRODUCTS.map(ProductViewData::fromProduct)
    }
}