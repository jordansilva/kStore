package com.jordansilva.kstore.util

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

fun onViewDisplayed(id: Int, matcher: Matcher<View>? = null) = matcher?.let {
    Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(id),
            ViewMatchers.isDisplayed(),
            matcher
        )
    )
} ?: Espresso.onView(Matchers.allOf(ViewMatchers.withId(id), ViewMatchers.isDisplayed()))

fun onViewDisplayedWithText(text: String) = Espresso.onView(Matchers.allOf(ViewMatchers.withText(text), ViewMatchers.isDisplayed()))

fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}