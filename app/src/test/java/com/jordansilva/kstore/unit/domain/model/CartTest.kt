package com.jordansilva.kstore.unit.domain.model

import com.google.common.truth.Truth.assertThat
import com.jordansilva.kstore.domain.model.Cart
import com.jordansilva.kstore.helper.TestUtil
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class CartTest {

    private lateinit var sut: Cart

    @Before
    fun setUp() {
        sut = Cart()
    }

    @Test
    fun `given the cart has two products, when I get the list of products, then it should return these two products`() {
        sut.add(PRODUCT_A)
        sut.add(PRODUCT_B)

        val expectedList = listOf(cartProductA(), cartProductB())
        assertThat(sut.products).containsExactlyElementsIn(expectedList)
    }

    @Test
    fun `given the cart is empty, when I add the a product two times to the cart, then it should have 1 product inside with quantity 2`() {
        assertThat(sut.products).isEmpty()

        sut.add(PRODUCT_A)
        sut.add(PRODUCT_A)

        val expectedList = arrayOf(cartProductA(2))
        assertThat(sut.products).containsExactlyElementsIn(expectedList)
    }

    @Test
    fun `given the cart has one item, when I remove this product from the cart, then it should have no products`() {
        sut.add(PRODUCT_A)
        assertThat(sut.products).isNotEmpty()

        sut.remove(PRODUCT_A.id)
        assertThat(sut.products).isEmpty()
    }

    @Test
    fun `given the cart has two items of the same product, when I remove one item of this product from the cart, then it should still have one item`() {
        sut.add(PRODUCT_A)
        sut.add(PRODUCT_A)
        val expectedListWith2Items = arrayOf(cartProductA(2))
        assertThat(sut.products).containsExactlyElementsIn(expectedListWith2Items)

        sut.remove(PRODUCT_A.id)
        val expectedListWith1Item = arrayOf(cartProductA())
        assertThat(sut.products).containsExactlyElementsIn(expectedListWith1Item)
    }

    @Test
    fun `given the cart has one item, when I remove an invalid product from the cart, then it should still have one product`() {
        sut.add(PRODUCT_A)
        assertThat(sut.products).isNotEmpty()

        sut.remove("INVALID")
        assertThat(sut.products).containsExactlyElementsIn(arrayOf(cartProductA()))
    }

    @Test
    fun `given the cart has two items of the same product, when I remove the product from the cart, then it should have no product in the cart`() {
        sut.add(PRODUCT_A)
        sut.add(PRODUCT_A)
        val expectedListWith2Items = arrayOf(cartProductA(2))
        assertThat(sut.products).containsExactlyElementsIn(expectedListWith2Items)

        sut.removeAll(PRODUCT_A.id)
        assertThat(sut.products).isEmpty()
    }

    @Test
    fun `given the cart is empty, when I add a two products to the cart, then the quantity of items should be two`() {
        assertThat(sut.products).isEmpty()
        sut.add(PRODUCT_A)
        sut.add(PRODUCT_B)
        assertThat(sut.quantityItems()).isEqualTo(2)
    }

    @Test
    fun `given the cart has two different products, when I add more of the products to the cart, then the quantity of products should remain the same and the amount of items should increase`() {
        sut.add(PRODUCT_A)
        sut.add(PRODUCT_B)
        assertThat(sut.products).hasSize(2)
        assertThat(sut.quantityItems()).isEqualTo(2)

        sut.add(PRODUCT_A)
        sut.add(PRODUCT_B)
        sut.add(PRODUCT_B)
        assertThat(sut.products).hasSize(2)
        assertThat(sut.quantityItems()).isEqualTo(5)
    }

    @Test
    fun `given the cart has two products with different quantity per product, then the cart total price should be the sum of the total price of each product times the quantity of that product`() {
        val priceA = PRODUCT_A.price.value
        val priceB = PRODUCT_A.price.value
        val expectedTotal = priceA + (priceB * 2.toBigDecimal())

        sut.add(PRODUCT_A)
        sut.add(PRODUCT_B)
        sut.add(PRODUCT_B)

        assertThat(sut.totalPrice()).isEqualTo(expectedTotal)
    }

    @Test
    fun `given the cart is empty and the total price is zero, when I add a single product to the cart, then the cart total price should be the same as the product added`() {
        val expectedTotal = PRODUCT_A.price.value

        assertThat(sut.products).isEmpty()
        assertThat(sut.totalPrice()).isEqualTo(BigDecimal.ZERO)

        sut.add(PRODUCT_A)
        assertThat(sut.totalPrice()).isEqualTo(expectedTotal)
    }

    @Test
    fun `given the cart is empty, then the total price should be zero`() {
        assertThat(sut.products).isEmpty()
        assertThat(sut.totalPrice()).isEqualTo(BigDecimal.ZERO)
    }

    private companion object {
        val PRODUCT_A = TestUtil.makeProduct("A")
        val PRODUCT_B = TestUtil.makeProduct("B")

        fun cartProductA(quantity: Int = 1) = TestUtil.makeCartProduct(PRODUCT_A, quantity)
        fun cartProductB(quantity: Int = 1) = TestUtil.makeCartProduct(PRODUCT_B, quantity)
    }
}