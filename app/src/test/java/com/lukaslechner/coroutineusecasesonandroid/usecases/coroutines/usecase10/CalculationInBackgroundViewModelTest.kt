package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CalculationInBackgroundViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performCalculation() should perform correct calculation`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val viewModel = CalculationInBackgroundViewModel(testDispatcher).apply {
            observe()
        }

        Assert.assertTrue(receivedUiStates.isEmpty())

        viewModel.performCalculation(1)

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "1",
            (receivedUiStates[1] as UiState.Success).result
        )

        receivedUiStates.clear()

        viewModel.performCalculation(2)

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "2",
            (receivedUiStates[1] as UiState.Success).result
        )

        receivedUiStates.clear()

        viewModel.performCalculation(3)

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "6",
            (receivedUiStates[1] as UiState.Success).result
        )
    }

    private fun CalculationInBackgroundViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}