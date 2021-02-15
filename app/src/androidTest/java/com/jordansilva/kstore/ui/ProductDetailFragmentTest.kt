package com.jordansilva.kstore.ui

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jordansilva.kstore.R
import com.jordansilva.kstore.data.repository.datasource.CartDataSource
import com.jordansilva.kstore.util.onViewDisplayed
import com.jordansilva.kstore.util.onViewDisplayedWithText
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProductDetailFragmentTest : KoinTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private var activity: Activity? = null

    private val cartDataSource: CartDataSource by inject()

    @Before
    fun setUp() {
        activityTestRule.scenario.onActivity { activity = it }
    }

    @After
    fun tearDown() {
        activity = null
        cartDataSource.newCart()
    }

    @Test
    fun givenListIsLoaded_whenProductIsClicked_thenShowProductPage() {
        onViewDisplayedWithText(PRODUCT_NAME).perform(click())
        onViewDisplayed(R.id.name, hasSibling(withId(R.id.price))).check(matches(withText(PRODUCT_NAME)))
        onViewDisplayed(R.id.price).check(matches(withText(PRODUCT_PRICE)))
        onViewDisplayed(R.id.image).check(matches(isDisplayed()))
    }

    @Test
    fun givenProductPageIsDisplayed_whenAddToCartClicked_thenCartBadgeIncrementsNumber() {
        onViewDisplayedWithText(PRODUCT_NAME).perform(click())
        onViewDisplayed(R.id.name, hasSibling(withId(R.id.price))).check(matches(withText(PRODUCT_NAME)))

        addProductToBasket()
        checkCartBadgeQuantity(1)

        repeat(5) { addProductToBasket() }
        checkCartBadgeQuantity(6)
    }

    @Test
    fun givenProductPageIsDisplayed_whenClickCartBadge_thenShowShoppingCartPage() {
        onViewDisplayedWithText(PRODUCT_NAME).perform(click())
        onViewDisplayed(R.id.shopping_cart).perform(click())
        onViewDisplayed(R.id.emptyShoppingCart).check(matches(isDisplayed()))
    }

    private fun addProductToBasket() {
        onViewDisplayed(R.id.addToBasket).perform(click())
        onView(withText(R.string.product_added_to_cart))
            .inRoot(withDecorView(not(activity!!.window.decorView))).check(matches(isDisplayed()))
    }

    private fun checkCartBadgeQuantity(quantity: Int) {
        onViewDisplayed(R.id.shopping_cart).check(matches(isDisplayed()))
        onViewDisplayed(R.id.cart_badge)
            .check(matches(isDisplayed()))
            .check(matches(withText(quantity.toString())))
    }

    private companion object {
        private const val PRODUCT_NAME = "Lidhult"
        private const val PRODUCT_PRICE = "₩1,035.00"
    }
}