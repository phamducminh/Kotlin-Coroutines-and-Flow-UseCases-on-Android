package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var calculationJob: Job? = null
    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        calculationJob = viewModelScope.launch {
            Timber.d("Coroutine Context: $coroutineContext")

            var result: BigInteger = BigInteger.ONE
            val computationDuration = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)

            }
            var resultString = ""
            val stringConversionDuration = measureTimeMillis {
                resultString = withContext(defaultDispatcher + CoroutineName("String Conversion Coroutine")) {
                    result.toString()
                }
            }
            uiState.value =
                UiState.Success(resultString, computationDuration, stringConversionDuration)
        }

        calculationJob?.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                Timber.d("Calculation was cancelled!")
            }
        }
    }

    private suspend fun calculateFactorialOf(number: Int) = withContext(defaultDispatcher) {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            yield()
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        Timber.d("Calculating Factorial Completed!")
        factorial
    }

    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}