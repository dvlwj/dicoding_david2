package dicoding.david.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import dicoding.david.footballapps.R.id.viewPager
import org.junit.Rule
import org.junit.Test


class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(viewPager)).check(matches(isDisplayed()))
        onView(withId(viewPager)).perform(swipeLeft())
        onView(withId(viewPager)).perform(swipeLeft())
        onView(withId(viewPager)).perform(swipeRight())
        Thread.sleep(4000)
    }
}