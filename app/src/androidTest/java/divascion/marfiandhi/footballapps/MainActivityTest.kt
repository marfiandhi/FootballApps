package divascion.marfiandhi.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import divascion.marfiandhi.footballapps.R.id.*
import divascion.marfiandhi.footballapps.view.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
/**
 * Created by Marfiandhi on 10/11/2018.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    val delay = 3000L

    @Test
    fun testAppBehaviour() {
        onView(withId(navigation)).check(matches(isDisplayed()))

        //Next Match Behaviour with Favorite and UnFavorite
        onView(withId(navigation_matches)).perform(click())
        testNextMatchesBehaviour()
        testDetailEventBehaviour("Added to favorite")
        onView(withId(navigation_favorites)).perform(click())
        testFavoriteEventBehaviour()
        testDetailEventBehaviour("Removed from favorite")
        onView(withId(navigation_favorites)).perform(click())
        onView(withId(matches_no_favorite)).check(matches(isDisplayed()))

        //Last Match Behaviour with Favorite and UnFavorite
        onView(withId(navigation_matches)).perform(click())
        onView(withText("LAST")).perform(click())
        testLastMatchesBehaviour()
        testDetailEventBehaviour("Added to favorite")
        onView(withId(navigation_favorites)).perform(click())
        testFavoriteEventBehaviour()
        testDetailEventBehaviour("Removed from favorite")
        onView(withId(navigation_favorites)).perform(click())
        onView(withId(matches_no_favorite)).check(matches(isDisplayed()))

    }

    private fun testNextMatchesBehaviour() {
        onView(withId(recycler_next)).check(matches(isDisplayed()))
        onView(withId(recycler_next)).check(matches(isDisplayed()))
        onView(withId(recycler_next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))
        onView(withId(recycler_next)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, click()))
    }
    private fun testLastMatchesBehaviour() {
        onView(withId(recycler_last)).check(matches(isDisplayed()))
        onView(withId(recycler_last)).check(matches(isDisplayed()))
        onView(withId(recycler_last)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))
        onView(withId(recycler_last)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, click()))
    }

    private fun testDetailEventBehaviour(text: String) {
        onView(withId(detailEventView)).check(matches(isDisplayed()))
        sleep(delay)
        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))
        onView(withId(detailEventView)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText(text)).check(matches(isDisplayed()))
        pressBack()

    }

    private fun testFavoriteEventBehaviour() {
        onView(withId(recycler_favorite)).check(matches(isDisplayed()))
        onView(withId(recycler_favorite)).check(matches(isDisplayed()))
        onView(withId(recycler_favorite)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

}