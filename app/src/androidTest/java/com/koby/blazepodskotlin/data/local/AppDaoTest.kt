package com.koby.blazepodskotlin.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.ListFragment
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.getOrAwaitValue
import com.koby.blazepodskotlin.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class WalletHistoryDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase

    private lateinit var dao: AppDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.appDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertWalletHistory() = runBlockingTest {
        val item = Item(
            1,"FAKE_NAME",
            "FAKE_POST_TITLE"
        )

        dao.insertItem(item)

        val allWalletHistory = dao.getAllItems().getOrAwaitValue()
        val daoWalletHistoryId = allWalletHistory[0].id
        assertThat(daoWalletHistoryId).isEqualTo(1)

    }

    @Test
    fun testLaunchFragmentInHiltContainer(){
        launchFragmentInHiltContainer<ListFragment> {  }
    }




}