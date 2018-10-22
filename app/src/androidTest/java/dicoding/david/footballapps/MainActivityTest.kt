package dicoding.david.footballapps

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import dicoding.david.footballapps.R.id.*
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import dicoding.david.footballapps.view.MainActivity
import org.hamcrest.Matcher


class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(viewPager)).check(matches(isDisplayed()))
        onView(withId(viewPager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(match_list_next_match)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(match_list_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
        Thread.sleep(1000)
        onView(withId(match_list_next_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
        Thread.sleep(1000)
        onView(withId(activity_detail)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
        onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
        Thread.sleep(1000)
        onView(withId(viewPager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(swipe_container_fav_match)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(swipe_container_fav_match)).perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
        Thread.sleep(4000)
    }

    private fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }
}