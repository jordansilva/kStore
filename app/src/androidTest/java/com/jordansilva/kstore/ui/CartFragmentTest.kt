package com.jordansilva.kstore.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jordansilva.kstore.R
import com.jordansilva.kstore.util.RecyclerViewMatcher
import com.jordansilva.kstore.util.onViewDisplayed
import com.jordansilva.kstore.util.onViewDisplayedWithText
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun givenEmptyCart_whenAddToCartClicked_andNavigateToCartPage_thenItDisplayProducts() {
        openProductAndAddToBasket(PRODUCT_NAME, PRODUCT_QUANTITY)
        openProductAndAddToBasket(ANOTHER_PRODUCT_NAME, ANOTHER_PRODUCT_QUANTITY)

        onViewDisplayed(R.id.shopping_cart).perform(click())
        onViewDisplayed(R.id.cartView).check(matches(isDisplayed()))

        onRecyclerViewItem(0, R.id.name).check(matches(withText(PRODUCT_NAME)))
        onRecyclerViewItem(0, R.id.quantity).check(matches(withText(PRODUCT_QUANTITY.toString())))
        onRecyclerViewItem(1, R.id.name).check(matches(withText(ANOTHER_PRODUCT_NAME)))
        onRecyclerViewItem(1, R.id.quantity).check(matches(withText(ANOTHER_PRODUCT_QUANTITY.toString())))
    }

    @Test
    fun givenCartHasProducts_whenClickToRemoveAnItem_thenProductIsRemovedFromCart() {
        openProductAndAddToBasket(PRODUCT_NAME, PRODUCT_QUANTITY)
        openProductAndAddToBasket(ANOTHER_PRODUCT_NAME, ANOTHER_PRODUCT_QUANTITY)

        onViewDisplayed(R.id.shopping_cart).perform(click())
        onViewDisplayed(R.id.cartView).check(matches(isDisplayed()))

        var quantity = PRODUCT_QUANTITY
        onRecyclerViewItem(0, R.id.name).check(matches(withText(PRODUCT_NAME)))

        while (quantity > 0) {
            onRecyclerViewItem(0, R.id.quantity).check(matches(withText(quantity.toString())))
            onRecyclerViewItem(0, R.id.reduceQuantity).perform(click())
            quantity--
        }
        onRecyclerViewItem(0, R.id.name).check(matches(withText(ANOTHER_PRODUCT_NAME)))
    }

    private fun openProductAndAddToBasket(productName: String, quantity: Int = 1) {
        onViewDisplayedWithText(productName).perform(click())
        repeat(quantity) {
            onViewDisplayed(R.id.addToBasket).perform(click())
            onView(withText(R.string.product_added_to_cart))
                .inRoot(withDecorView(not(activityTestRule.activity.window.decorView))).check(matches(isDisplayed()));
        }
        Espresso.pressBack()
    }

    private fun onRecyclerViewItem(position: Int, id: Int) = onView(RecyclerViewMatcher(R.id.recyclerView).atPositionOnView(position, id))

    private companion object {
        private const val PRODUCT_NAME = "Lidhult"
        private const val PRODUCT_QUANTITY = 5
        private const val ANOTHER_PRODUCT_NAME = "Henriksdal"
        private const val ANOTHER_PRODUCT_QUANTITY = 2
    }
}