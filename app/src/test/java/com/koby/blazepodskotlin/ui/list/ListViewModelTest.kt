package com.koby.blazepodskotlin.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.getOrAwaitValueTest
import com.koby.blazepodskotlin.repository.RepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
    import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ListViewModelTest() {

    private lateinit var viewModel: ListViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = ListViewModel(RepositoryTest())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `insert Item with empty field`() {
        val item = Item(
            1, "FAKE_NAME",
            "FAKE_POST_TITLE"
        )
        runBlockingTest {
                viewModel.saveFavoriteItem(item)
                val favoriteItem = viewModel.getFavoriteItems().getOrAwaitValueTest()
                assertTrue(favoriteItem.contains(item))
        }
    }
}