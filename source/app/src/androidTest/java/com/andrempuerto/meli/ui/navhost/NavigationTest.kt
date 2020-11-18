package com.andrempuerto.meli.ui.navhost

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.andrempuerto.meli.R
import com.andrempuerto.meli.launchFragmentInHiltContainer
import com.andrempuerto.meli.ui.home.HomeFragment
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.andrempuerto.meli.ui.product.search.SearchProductFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navController: NavController

    @MockK
    lateinit var viewModel: ProductViewModel

    @Before
    fun initTest() {
        hiltRule.inject()
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.mobile_navigation)

    }

    @After
    fun finishTest() {
    }

    @Test
    fun navHomeFragmentToProductsFragmentTest() {
        launchFragmentInHiltContainer<HomeFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
            Navigation.setViewNavController(this.requireView(), navController)
        }

        onView(withId(R.id.cv_col)).perform(click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.view_list_products)
    }

    @Test
    fun navDrawerLayoutToSettingsFragmentTest() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_settings))

        onView(withId(androidx.preference.R.id.recycler_view))
            .check(matches(isDisplayed()))

        onView(withText("Tema"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validateSearchProductListTest() {
        launchFragmentInHiltContainer<SearchProductFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
            Navigation.setViewNavController(this.requireView(), navController)
        }

        navController.navigate(R.id.view_list_products)

        onView(withId(R.id.svProduct)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("android"),
            pressImeActionButton()
        )

        onView(isRoot()).perform(waitFor(2500))

        onView(withId(R.id.rvProducts))
            .check(matches(isDisplayed()))

    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }
}
