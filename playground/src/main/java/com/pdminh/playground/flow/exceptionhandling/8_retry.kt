package com.pdminh.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    launch {
        stockFlow()
            .catch { throwable ->
                println("Handle exception in catch() operator $throwable")
            }
            .collect { stockData ->
                println("Collected $stockData")
            }
    }
}

private fun stockFlow(): Flow<String> = flow {

    repeat(5) { index ->

        delay(1_000)

        if (index < 4) {
            emit("New Stock data")
        } else {
            throw NetworkException("Network Request Failed!")
        }
    }
}
//    .retry(retries = 3) { cause ->
//        println("Enter retry() with $cause")
//        delay(1_000)
//        cause is NetworkException
//    }
    .retryWhen { cause, attempt ->
        println("Enter retry() with $cause")
        delay(1_000 * (attempt + 1))
        cause is NetworkException
    }

class NetworkException(message: String) : Exception(message)
