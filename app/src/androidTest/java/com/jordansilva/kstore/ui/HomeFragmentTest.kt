package com.jordansilva.kstore.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jordansilva.kstore.R
import com.jordansilva.kstore.util.RecyclerViewMatcher
import com.jordansilva.kstore.util.onViewDisplayed
import com.jordansilva.kstore.util.onViewDisplayedWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun whenListIsLoaded_thenDisplayAtLeast3Items() {
        verifyListHasItems(3)
        onViewDisplayedWithText(PRODUCT_NAME)
            .check(matches(withText(PRODUCT_NAME)))
    }

    @Test
    fun whenListIsLoadedAndProductIsClicked_thenDisplayProductPage() {
        onViewDisplayedWithText(PRODUCT_NAME).perform(click())
        onViewDisplayed(R.id.name, hasSibling(withId(R.id.price))).check(matches(withText(PRODUCT_NAME)))
    }

    private fun verifyListHasItems(position: Int = 0) {
        sleep(600)
        val imageView = onView(RecyclerViewMatcher(R.id.recyclerView).atPositionOnView(position, R.id.image))
        imageView.check(matches(isDisplayed()))
    }

    private companion object {
        private const val PRODUCT_NAME = "Lidhult"
        private const val PRODUCT_PRICE = "₩1,035.00"
    }
}