package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PerformNetworkRequestsConcurrentlyViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequestsSequentially should load data sequentially`() = runTest {

        // Arrange
        val responseDelay = 1000L
        val fakeApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        viewModel.observe()

        // Act
        viewModel.performNetworkRequestsSequentially()

//        advanceTimeBy(3000)
//        runCurrent()

        advanceUntilIdle()

        // Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ), receivedUiStates
        )
        Assert.assertEquals(3000, currentTime)
    }

    @Test
    fun `performNetworkRequestsConcurrently should load data concurrently`() = runTest {
        // Arrange
        val responseDelay = 1000L
        val fakeApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        viewModel.observe()

        // Act
        viewModel.performNetworkRequestsConcurrently()
        advanceUntilIdle()

        // Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ), receivedUiStates
        )
        Assert.assertEquals(1000L, currentTime)
    }

    private fun PerformNetworkRequestsConcurrentlyViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }

}