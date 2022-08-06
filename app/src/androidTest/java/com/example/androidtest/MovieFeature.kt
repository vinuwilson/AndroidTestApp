package com.example.androidtest

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFeature {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Android Test")
    }

    @Test
    fun displayListOfMovies() {

        assertRecyclerViewItemCount(R.id.movie_list, 25)

        onView(
            allOf(
                withId(R.id.movie_title),
                isDescendantOfA(nthChildOf(withId(R.id.movie_list), 0))
            )
        )
            .check(matches(withText("Dunkirk")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.movie_year),
                isDescendantOfA(nthChildOf(withId(R.id.movie_list), 0))
            )
        )
            .check(matches(withText("2017")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.movie_genre),
                isDescendantOfA(nthChildOf(withId(R.id.movie_list), 0))
            )
        )
            .check(matches(withText("History")))
            .check(matches(isDisplayed()))
    }


    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}