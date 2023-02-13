package com.haomins.reader.testutil

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Create an artificial delay to wait for ui operations, without blocking.
 * @see: https://stackoverflow.com/questions/52818524/delay-test-in-espresso-android-without-freezing-main-thread
 *
 * @param delay how long to wait for in milliseconds
 * @return [ViewAction]
 */
fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "wait for $delay milliseconds"
        }

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}

/**
 * A matcher used to match items in recyclerview
 * @see: https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 *
 * @param position item position in the recyclerview adapter
 * @return [BoundedMatcher] a matcher for an item in recyclerview
 */
fun atPosition(position: Int, itemMatcher: Matcher<View>): BoundedMatcher<View, RecyclerView> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has item at position: $position")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            val viewHolder = item?.findViewHolderForAdapterPosition(position)
            return if (viewHolder == null) false
            else {
                itemMatcher.matches(viewHolder.itemView)
            }
        }

    }
}