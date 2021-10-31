package com.koby.blazepodskotlin.ui.list

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.koby.blazepodskotlin.R
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.launchFragmentInHiltContainer
import com.koby.blazepodskotlin.repository.RepositoryAndroidTest
import com.koby.blazepodskotlin.ui.adapters.CustomAdaper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
class ListFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickAddItemFab_navigateToAddNewItemFragment(){
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<ListFragment> {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(),navController)
        }

        onView(withId(R.id.addItemFab)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.addItemFragment)
    }


}