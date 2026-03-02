package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable") // print
    }
//    val scope = CoroutineScope(Job() + exceptionHandler)
    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("Coroutine 1 starts") // print
        delay(50)
        println("Coroutine 1 fails") // print
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts") // print
        delay(500)
        println("Coroutine 2 completed") // not print if Job, print if SupervisorJob
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 got cancelled!") // print if Job, not print if SupervisorJob
        }
    }

    Thread.sleep(1000)

    println("Scope got cancelled: ${!scope.isActive}") // true if Job, false if SupervisorJob
}