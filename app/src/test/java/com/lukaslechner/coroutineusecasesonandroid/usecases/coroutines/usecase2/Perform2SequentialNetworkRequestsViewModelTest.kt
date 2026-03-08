package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class Perform2SequentialNetworkRequestsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success when network request is successful`() {

        // Arrange
        val fakeApi = FakeSuccessApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        // Act
        viewModel.perform2SequentialNetworkRequest()

        // Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10)
            ), receivedUiStates
        )
    }

    @Test
    fun `should return Error when first network request fails`() {

        // Arrange
        val fakeApi = FakeVersionsErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        // Act
        viewModel.perform2SequentialNetworkRequest()

        // Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed!")
            ), receivedUiStates
        )
    }

    @Test
    fun `should return Error when second network request fails`() {

        // Arrange
        val fakeApi = FakeFeaturesErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        // Act
        viewModel.perform2SequentialNetworkRequest()

        // Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed!")
            ), receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}