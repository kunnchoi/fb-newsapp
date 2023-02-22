package com.jandas.newsapp.ui.screen.news

import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.jandas.newsapp.FakeRepository
import com.jandas.newsapp.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NewsViewModelTest {
    // Subject under test
    private lateinit var newsViewModel: NewsViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var newsRepository: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        // We initialise the
        newsRepository = FakeRepository()
        newsRepository.mockNewsResponse()

        newsViewModel = NewsViewModel(
            newsRepository,
            ImageLoader.Builder(InstrumentationRegistry.getInstrumentation().targetContext).build()
        )
    }

    @Test
    fun loadAllNewsFromRepository_loadingTogglesAndDataLoaded() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        // Then progress indicator is shown
        assertThat(newsViewModel.uiState.first().isLoading).isTrue()

        // Execute pending coroutines actions
        advanceUntilIdle()

        // Then progress indicator is hidden
        assertThat(newsViewModel.uiState.first().isLoading).isFalse()

        // And data correctly loaded
        assertThat(newsViewModel.uiState.first().items).hasSize(1)
    }

}