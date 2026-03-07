package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandle = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception: $throwable in CoroutineExceptionHandler")
    }
    val scope = CoroutineScope(Job())

    scope.launch(exceptionHandle) {

        launch {
            println("Starting coroutine 1")
            delay(100)
            throw RuntimeException()
        }

        launch {
            println("Starting coroutine 2")
            delay(3000)
            println("Coroutine 2 completed")
        }
    }

    Thread.sleep(5000)
}